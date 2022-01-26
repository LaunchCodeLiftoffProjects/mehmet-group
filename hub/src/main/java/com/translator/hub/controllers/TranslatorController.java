package com.translator.hub.controllers;

import com.translator.hub.data.LangRepository;
import com.translator.hub.data.TranslatorRepository;
import com.translator.hub.models.DTO.TranslatorEditFormDTO;
import com.translator.hub.models.DTO.TranslatorLogFormDTO;
import com.translator.hub.models.DTO.TranslatorRegFormDTO;
import com.translator.hub.models.Language;
import com.translator.hub.models.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

import static org.thymeleaf.util.StringUtils.capitalize;
import static org.thymeleaf.util.StringUtils.capitalizeWords;


@Controller
@RequestMapping("translator")
public class TranslatorController {

    @Autowired
    private TranslatorRepository translatorRepository;

    @Autowired
    private LangRepository langRepository;

    private static final String translationSessionKey = "translator";

    public Translator getTranslatorFromSession(HttpSession session) {
        Integer translatorId = (Integer) session.getAttribute(translationSessionKey);
        if (translatorId == null) {
            return null;
        }

        Optional<Translator> translator = translatorRepository.findById(translatorId);

        if (translator.isEmpty()) {
            return null;
        }

        return translator.get();
    }

    private static void setTranslatorInSession(HttpSession session, Translator translator) {
        session.setAttribute(translationSessionKey, translator.getId());
    }

    //respond for localhost:8080/translator
    @GetMapping
    public String displayTranslators(Model model) {

        model.addAttribute("title", "All Translators");
        model.addAttribute("translators", translatorRepository.findAll());

        return "translator/index";
    }


    //lives at localhost:8080/translator/register
    @GetMapping("/register")
    public String displayRegistrationForm(Model model) {
        model.addAttribute(new TranslatorRegFormDTO());
        model.addAttribute("title", "Register");
        return "translator/register";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute @Valid TranslatorRegFormDTO translatorRegFormDTO,
                                          Errors errors, HttpServletRequest request, @RequestParam("image") MultipartFile multipartFile,
                                          Model model) throws IOException {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Register");
            return "translator/register";
        }

        Translator existingTranslator = translatorRepository.findByEmail(translatorRegFormDTO.getEmail());

        if (existingTranslator != null) {
            errors.rejectValue("firstName", "firstName already exists", "A user with that username already exists");
            model.addAttribute("title", "Register");
            return "translator/register";
        }

        String password = translatorRegFormDTO.getPassword();
        String verifyPassword = translatorRegFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            model.addAttribute("title", "Register");
            return "translator/register";
        }

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Translator newTranslator = new Translator(capitalize(translatorRegFormDTO.getFirstName()), capitalize(translatorRegFormDTO.getLastName()), translatorRegFormDTO.getEmail(),
                capitalizeWords(translatorRegFormDTO.getLanguage()), capitalize(translatorRegFormDTO.getAddress()), capitalize(translatorRegFormDTO.getBio()), translatorRegFormDTO.getPassword());
        newTranslator.setImage(fileName);

        //adds languages from the register form to langRepository if they don't already exist
        splitAndSave(newTranslator.getLanguage());


        Translator savedTranslator = translatorRepository.save(newTranslator);
        setTranslatorInSession(request.getSession(), newTranslator);
        String uploadDir = "../hub/src/main/resources/static/translator-photos/" + savedTranslator.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return "redirect:/translator/detail?translatorId=" + savedTranslator.getId();
    }

    @GetMapping("/login")
    public String displayLoginForm(Model model) {
        model.addAttribute(new TranslatorLogFormDTO());
        model.addAttribute("title", "Log In");
        return "translator/login";
    }

    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute @Valid TranslatorLogFormDTO translatorLogFormDTO,
                                   Errors errors, HttpServletRequest request,
                                   Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Log In");
            return "translator/login";
        }

        Translator theTranslator = translatorRepository.findByEmail(translatorLogFormDTO.getEmail());

        if (theTranslator == null) {
            errors.rejectValue("email", "translator.invalid", "The given email does not exist");
            model.addAttribute("title", "Log In");
            return "translator/login";
        }

        String password = translatorLogFormDTO.getPassword();

        if (!theTranslator.isMatchingPassword(password)) {
            errors.rejectValue("password", "password.invalid", "Invalid password");
            model.addAttribute("title", "Log In");
            return "translator/login";
        }
        setTranslatorInSession(request.getSession(), theTranslator);

        return "redirect:";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/translator/login";
    }

    //lives at localhost:8080/translator/detail?translatorId=3
    @GetMapping("detail")
    public String displayEventDetails(@RequestParam(required = false) Integer translatorId, Model model) {
        Optional<Translator> result = translatorRepository.findById(translatorId);
        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Translator Id" + translatorId);
        } else {
            Translator translator = result.get();
            model.addAttribute("title", translator.getFirstName() + " Details");
            model.addAttribute("translator", translator);
        }
        return "translator/detail";
    }

    //lives at localhost:8080/translator/editTranslator?translatorId=3
    @GetMapping("/editTranslator")
    public String showEditTranslatorForm(@RequestParam(required = false) Integer translatorId, Model model) {
        Optional<Translator> result = translatorRepository.findById(translatorId);
        if (result.isEmpty()) {
            model.addAttribute("title", "Translator by Id" + translatorId + "Not found!");
            return "translator/index";
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
        return "translator/editTranslatorForm";
    }

    @PostMapping("/editTranslator")
    public String processTranslatorEditForm(@ModelAttribute @Valid TranslatorEditFormDTO editedTranslator, String translatorId, Errors errors, HttpServletRequest request, @RequestParam("image") MultipartFile multipartFile,
                                            Model model) throws IOException {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit");
            return "translator/editTranslatorForm";
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
        if(fileName != null && !fileName.isEmpty() && !fileName.isBlank()){
            translatorUpdated.setImage(fileName); //setting image value to the database

            //we are setting the file directory address
            String uploadDir = "../hub/src/main/resources/static/translator-photos/" + translatorUpdated.getId();

            //we are copying and uploading the image to the project directory
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }

        //adds any new languages added in the edit to language repository
        splitAndSave(translatorUpdated.getLanguage());


        translatorRepository.save(translatorUpdated);


//        return "redirect:/translator/detail?translatorId=" + translatorUpdated.getId();
        return "redirect:";
    }

    //lives at localhost:8080/translator/deleteTranslator?translatorId=3
    @PostMapping("/deleteTranslator")
    public String deleteTranslator(@RequestParam(required = false) Integer translatorId, Model model) {

        translatorRepository.deleteById(translatorId);
        return "redirect:";
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

