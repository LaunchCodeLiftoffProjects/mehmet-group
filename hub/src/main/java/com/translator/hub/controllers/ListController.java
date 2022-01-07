package com.translator.hub.controllers;

import com.translator.hub.data.LangRepository;
import com.translator.hub.data.TranslatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;



@Controller
public class ListController {

    @Autowired
    private TranslatorRepository translatorRepository;

/*    @Autowired
    private LangRepository languageRepository;*/

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
      //  model.addAttribute("language", languageRepository.findAll());
        return "translator/viewtranslators";
    }

}

