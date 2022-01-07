package com.translator.hub.controllers;

import com.translator.hub.data.LangRepository;
import com.translator.hub.data.TranslatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;


<<<<<<< HEAD

@Controller
=======
@RequestMapping(value = "translator")
>>>>>>> d7eb3b0f3cba027a5bce5529931f0a51449e8f93
public class ListController {

    @Autowired
    private TranslatorRepository translatorRepository;

<<<<<<< HEAD
/*    @Autowired
    private LangRepository languageRepository;*/
=======
    @Autowired
    private LangRepository langRepository;
>>>>>>> d7eb3b0f3cba027a5bce5529931f0a51449e8f93

/*
    static HashMap<String, String> columnChoices = new HashMap<>();

    public ListController () {
        columnChoices.put("all", "All");
        columnChoices.put("translator", "Translator");
        columnChoices.put("language", "Language");
    }*/

    @GetMapping(value="/viewtranslators")
    public String findAllTranslators(Model model) {
        model.addAttribute("translators", translatorRepository.findAll());
<<<<<<< HEAD
      //  model.addAttribute("language", languageRepository.findAll());
=======
        model.addAttribute("language", langRepository.findAll());
>>>>>>> d7eb3b0f3cba027a5bce5529931f0a51449e8f93
        return "translator/viewtranslators";
    }

}

