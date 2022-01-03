package com.translator.hub.controllers;

import com.translator.hub.data.LangRepository;
import com.translator.hub.data.TranslatorRepository;
import com.translator.hub.data.UserRepository;
import com.translator.hub.models.Language;
import com.translator.hub.models.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class HomeController {
    //connect to repositories
    @Autowired
    private LangRepository langRepository;

    @Autowired
    private TranslatorRepository translatorRepository;

    @Autowired
    private UserRepository userRepository;

    private List<Translator> translatorsList;


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
        return "add";
    }

    //** Anita
//    //allows use to display translators
//    @GetMapping("view/{jobId}")
//    public String displayViewJob(Model model, @PathVariable int jobId) {
////    extends CrudRepository<Skill, Integer>
//
//        return "view";
//    }

    @GetMapping
    public String index() {
        return "index";
    }



//    processing translators being added to repository ** I need advice here -Lindsey
    @PostMapping("add")
    public String processTranslatorForm(@ModelAttribute @Valid Translator newTranslator, Errors errors, Model model, @RequestParam int translatorId, @RequestParam List<Integer>language) {
        if (errors.hasErrors()) {

            return "add";
        }

//        Searches for Translators by language
        List<Language> langObjs = (List<Language>) langRepository.findAllById(language);
        newTranslator.setLanguage(langObjs);

//        Create a new translator if not finding one by ID or
        Translator translator = translatorRepository.findById(translatorId).orElse(new Translator());
        newTranslator.setTranslator(translator);

        return "redirect:";

    }

    }
