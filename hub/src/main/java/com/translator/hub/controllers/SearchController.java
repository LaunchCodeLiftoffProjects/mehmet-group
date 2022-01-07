package com.translator.hub.controllers;


import com.translator.hub.data.TranslatorRepository;
import com.translator.hub.models.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("search")
public class SearchController {

    @Autowired
    private TranslatorRepository translatorRepository;


    @RequestMapping("")
    public String search(Model model) {
        model.addAttribute("search", "search");
        return "search";
    }

    @PostMapping("results")
    public String displaySearchResults(Model model, @RequestParam String language1, @RequestParam String language2){
        Iterable<Translator> translators;


        return "search";
    }



}
