package com.translator.hub.controllers;

import com.translator.hub.data.TranslatorRepository;
import com.translator.hub.models.DTO.TranslatorLogFormDTO;
import com.translator.hub.models.DTO.TranslatorRegFormDTO;
import com.translator.hub.models.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
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

@Controller
@RequestMapping("translator")
public class TranslatorController {

    @Autowired
    private TranslatorRepository translatorRepository;

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
            errors.rejectValue("email", "email.alreadyexists", "A user with that email already exists");
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
        Translator newTranslator = new Translator(translatorRegFormDTO.getFirstName(), translatorRegFormDTO.getLastName(), translatorRegFormDTO.getEmail(), translatorRegFormDTO.getLanguage(), translatorRegFormDTO.getAddress(), translatorRegFormDTO.getBio(), translatorRegFormDTO.getPassword());
        newTranslator.setImage(fileName);
        Translator savedTranslator = translatorRepository.save(newTranslator);
        setTranslatorInSession(request.getSession(), newTranslator);
        String uploadDir = "../hub/src/main/resources/static/translator-photos/" + savedTranslator.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return "redirect:";
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

    //lives at localhost:8080/detail?translatorId=3
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


//trying to add the correct mapping to view translators - 1/6/22/AGB
 //added lines 155-163

@GetMapping ("")
public String index (Model model){
    model.addAttribute("translators", translatorRepository.findAll());


    //responds with a list of all employers in the database
    return "translator/viewtranslators";//or should this be return employers/index?
}
}

//    @GetMapping("viewtranslators/{translatorId}")//path parameter translatorId. This piece of data customizes the response
//    public String displayViewTranslator(Model model, @PathVariable int translatorId) {
//
//        Optional optTranslator = translatorRepository.findById(translatorId);
//        if (optTranslator.isPresent()) {
//            Translator translator = (Translator) optTranslator.get();
//            model.addAttribute("translator", translator);
//
//            return "translator/viewtranslators";
//        } else {
//            return "redirect:../";
//        }
//    }

