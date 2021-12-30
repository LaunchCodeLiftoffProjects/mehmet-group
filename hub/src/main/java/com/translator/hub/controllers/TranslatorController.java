package com.translator.hub.controllers;

import com.translator.hub.data.TranslatorRepository;
import com.translator.hub.models.DTO.LoginFormDTO;
import com.translator.hub.models.DTO.TranslatorLogFormDTO;
import com.translator.hub.models.DTO.TranslatorRegFormDTO;
import com.translator.hub.models.Translator;
import com.translator.hub.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
//@RequestMapping("translator")
public class TranslatorController {

    @Autowired
    TranslatorRepository translatorRepository;

    private static final String translationSessionKey = "translator";

    public Translator getTranslatorFromSession(HttpSession session) {
        Integer translatorId = (Integer) session.getAttribute(translationSessionKey);
        if (translatorId ==null) {
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
                                          Errors errors, HttpServletRequest request,
                                          Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Register");
            return "translator/register";
        }

        Translator existingTranslator = translatorRepository.findByEmail(translatorRegFormDTO.getEmail());

        if (existingTranslator != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
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

        Translator newUser = new Translator(translatorRegFormDTO.getFirstName(), translatorRegFormDTO.getLastName(),translatorRegFormDTO.getLanguage(), translatorRegFormDTO.getBio(), translatorRegFormDTO.getAddress(), translatorRegFormDTO.getImage(), translatorRegFormDTO.getEmail(), translatorRegFormDTO.getPassword());
        translatorRepository.save(newUser);
        setTranslatorInSession(request.getSession(), newUser);

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
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/translator/login";
    }

}
