package com.translator.hub.controllers;

import com.translator.hub.models.DTO.UserForm;
import com.translator.hub.models.User;
import com.translator.hub.service.EmailExistsException;
import com.translator.hub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;


@Controller
public class UserController {

    @Autowired
    UserService userService;

    protected static final String MESSAGE_KEY = "message";

    @ModelAttribute("user")
    public User getLoggedInUser(Principal principal) {
        if (principal != null)
            return userService.findByEmail(principal.getName());
        return null;
    }

    @GetMapping(value = "/register")
    public String registerForm(Model model) {
        model.addAttribute(new UserForm());
        model.addAttribute("title", "Register");
        return "user/register";
    }

    @PostMapping(value = "/register")
    public String register(@ModelAttribute @Valid UserForm userForm, Errors errors) {

        if (errors.hasErrors())
            return "user/register";

        try {
            System.out.println("---------------------------------");
            System.out.println("userForm.FirstName: " + userForm.getFirstName());
            userService.save(userForm);
        } catch (EmailExistsException e) {
            errors.rejectValue("email", "email.alreadyexists", e.getMessage());
            return "register";
        }

        return "redirect:/user/login";
    }

    @GetMapping(value = "/login")
    public String login(Model model, Principal user, String error, String logout) {

        if (user != null)
            return "redirect:/";

        if (error != null)
            model.addAttribute(MESSAGE_KEY, "danger|Your username and password are invalid");

        if (logout != null)
            model.addAttribute(MESSAGE_KEY, "info|You have logged out");

        return "user/login";
    }

}
