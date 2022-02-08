package com.translator.hub.controllers;
import com.translator.hub.data.LangRepository;
import com.translator.hub.data.TestimonialRepository;
import com.translator.hub.data.TranslatorRepository;
import com.translator.hub.models.DTO.TranslatorEditFormDTO;
import com.translator.hub.models.Language;
import com.translator.hub.models.Testimonial;
import com.translator.hub.models.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;
import static org.thymeleaf.util.StringUtils.capitalize;
import static org.thymeleaf.util.StringUtils.capitalizeWords;

@Controller
@RequestMapping("admin")
public class AdminController {


    @Autowired
    private TranslatorRepository translatorRepository;

    @Autowired
    private LangRepository langRepository;

    @Autowired
    private TestimonialRepository testimonialRepository;

    //localhost:8080/admin/manageTranslators
    //localhost:8080/admin/updateTranslator?translatorId=3
    //lives at localhost:8080/admin/translatorDetail?translatorId=3
    //lives at localhost:8080/admin/deleteTranslator?translatorId=3


    //respond for localhost:8080/admin/manageTranslators
    @GetMapping("manageTranslators")
    public String displayTranslators(Model model) {
        model.addAttribute("title", "All Translators");
        model.addAttribute("translators", translatorRepository.findAll());
        return "admin/manageTranslators";
    }

    //respond for localhost:8080/admin/manageTestimonials
    @GetMapping("manageTestimonials")
    public String displayTestimonials(Model model) {
        model.addAttribute("title", "All Testimonials");
        model.addAttribute("testimonials", testimonialRepository.findAll());
        return "admin/manageTestimonials";
    }

    //respond for localhost:8080/admin/manageLanguages
    @GetMapping("manageLanguages")
    public String displayLanguages(Model model) {
        model.addAttribute("title", "All Languages");
        model.addAttribute("languages", langRepository.findAll());
        return "admin/manageLanguages";
    }

    //lives at localhost:8080/admin/translatorDetail?translatorId=3
    @GetMapping("translator/Detail")
    public String displayEventDetails(@RequestParam(required = false) Integer translatorId, Model model) {
        Optional<Translator> result = translatorRepository.findById(translatorId);
        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Translator Id" + translatorId);
        } else {
            Translator translator = result.get();
            model.addAttribute("title", translator.getFirstName() + " Details");
            model.addAttribute("translator", translator);
        }
        return "admin/translatorDetail";
    }

    //lives at localhost:8080/admin/updateTranslator?translatorId=3
    @GetMapping("updateTranslator")
    public String showEditTranslatorForm(@RequestParam(required = false) Integer translatorId, Model model) {
        Optional<Translator> result = translatorRepository.findById(translatorId);
        if (result.isEmpty()) {
            model.addAttribute("title", "Translator by Id" + translatorId + "Not found!");
            return "admin/manageTranslators";
        }
        Translator translator = result.get();
        TranslatorEditFormDTO translatorToEdit = new TranslatorEditFormDTO();
        translatorToEdit.setFirstName(translator.getFirstName());
        translatorToEdit.setLastName(translator.getLastName());
        translatorToEdit.setLanguage(translator.getLanguage());
        translatorToEdit.setAddress(translator.getAddress());
        translatorToEdit.setEmail(translator.getEmail());
        translatorToEdit.setBio(translator.getBio());
        model.addAttribute("title", "Edit " + translator.getFirstName() + " detail");
        model.addAttribute("translatorUpdate", translatorToEdit);
        model.addAttribute("translatorId", translatorId);
        return "admin/updateTranslator";
    }

    //lives at localhost:8080/admin/updateTranslator?translatorId=3
    @PostMapping("/updateTranslator")
    public String processTranslatorEditForm(@ModelAttribute @Valid TranslatorEditFormDTO editedTranslator, String translatorId, Errors errors, HttpServletRequest request, @RequestParam("image") MultipartFile multipartFile,
                                            Model model) throws IOException {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Update");
            return "admin/updateTranslator";
        }

        //Updating Translator object fields
        Translator translatorUpdated = translatorRepository.findById(Integer.parseInt(translatorId)).orElse(null);
        translatorUpdated.setFirstName(capitalize(editedTranslator.getFirstName()));
        translatorUpdated.setLastName(capitalize(editedTranslator.getLastName()));
        translatorUpdated.setEmail(editedTranslator.getEmail());
        translatorUpdated.setAddress(capitalize(editedTranslator.getAddress()));
        translatorUpdated.setLanguage(capitalizeWords(editedTranslator.getLanguage()));
        translatorUpdated.setBio(capitalize(editedTranslator.getBio()));
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        //This is where Translator image is being checked to see if it needs to be updated
        //There is default value for all Translator images, so if there is no new value
        //being passed, we do not need to update Translator image field
        if (fileName != null && !fileName.isEmpty() && !fileName.isBlank()) {
            translatorUpdated.setImage(fileName); //setting image value to the database

            //we are setting the file directory address
            String uploadDir = "../hub/src/main/resources/static/translator-photos/" + translatorUpdated.getId();

            //we are copying and uploading the image to the project directory
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }

        //adds any new languages added in the edit to language repository
        splitAndSave(translatorUpdated.getLanguage());

        translatorRepository.save(translatorUpdated);

        // return "redirect:/admin/detail?translatorId=" + translatorUpdated.getId();
        return "redirect:/admin/manageTranslators";
    }


    //lives at localhost:8080/admin/deleteTranslator?translatorId=3
    @PostMapping("/deleteTranslator")
    public String deleteTranslator(@RequestParam(required = false) Integer translatorId, Model model) {
        translatorRepository.deleteById(translatorId);
        return "redirect:/admin/manageTranslators";
    }

    //lives at localhost:8080/admin/approveTestimonial?testimonialId=3
    @PostMapping("/approveTestimonial")
    public String approveTestimonial(@RequestParam(required = false) Integer testimonialId, Model model) {
        Testimonial approved = testimonialRepository.findById(testimonialId).orElse(null);
        approved.setApproved(true);
        testimonialRepository.save(approved);
        return "redirect:/admin/manageTestimonials";
    }

    //lives at localhost:8080/admin/deleteTestimonial?testimonialId=3
    @PostMapping("/deleteTestimonial")
    public String deleteTestimonial(@RequestParam(required = false) Integer testimonialId, Model model) {
        testimonialRepository.deleteById(testimonialId);
        return "redirect:/admin/manageTestimonials";
    }

    //lives at localhost:8080/admin/deleteTestimonial?testimonialId=3
    @PostMapping("/deleteLanguage")
    public String deleteLanguage(@RequestParam(required = false) Integer languageId, Model model) {
        langRepository.deleteById(languageId);
        return "redirect:/admin/manageLanguages";
    }

    public void splitAndSave(String languageInput) {
        String[] languages;
        languages = languageInput.replaceAll(",\\s|\\s", ",").split(",");
        for (String language : languages) {
            if (!language.toLowerCase().equals("english")) {
                Language existingLanguage = langRepository.findByName(language);
                if (existingLanguage == null) {
                    language = capitalize(language);
                    Language newLanguage = new Language(language);
                    langRepository.save(newLanguage);
                }
            }
        }
    }
}
