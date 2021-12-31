package com.translator.hub.controllers;

import com.translator.hub.data.LangRepository;
import com.translator.hub.data.TranslatorRepository;
import com.translator.hub.data.UserRepository;
import com.translator.hub.models.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {
    //connect to repositories
    @Autowired
    private LangRepository langRepository;

    @Autowired
    private TranslatorRepository translatorRepository;

    @Autowired
    private UserRepository userRepository;

//    private List<TranslatorsList>translatorsList;

    //
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("title", "Translators");

        return "index";
    }

    //add translators to repository using registration form
    @GetMapping("add")
    public String displayTranslatorRegForm(Model model) {
        model.addAttribute(new Translator());
        model.addAttribute("translator", translatorRepository);
        model.addAttribute("language", langRepository);
    }


    @GetMapping
    public String index() {
        return "index";
    }

    }

    //processing translators being added to repository ** Need help on this
//    @PostMapping("add")
//    public String processTranslatorForm(@ModelAttribute @Valid Translator newTranslator, Error errors, Model model, @RequestParam int translatorId, @RequestParam List<Integer>language) {

    }
