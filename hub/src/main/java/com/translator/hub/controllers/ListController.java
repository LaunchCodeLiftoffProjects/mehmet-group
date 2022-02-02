package com.translator.hub.controllers;

import com.translator.hub.data.LangRepository;
import com.translator.hub.data.TranslatorRepository;
import com.translator.hub.models.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;


@Controller
public class ListController {

    @Autowired
    private TranslatorRepository translatorRepository;


    //uncommenting 24-33 beause it might be needed to list the translators
    // by language AGB 1/23
    @Autowired
    private LangRepository languageRepository;

    static HashMap<String, String> columnChoices = new HashMap<>();

    public ListController() {
        columnChoices.put("all", "All");
        columnChoices.put("translator", "Translator");
        columnChoices.put("language", "Language");
    }

    @GetMapping(value = "/viewtranslators")
    public String findAllTranslators(Model model) {
        model.addAttribute("translators", translatorRepository.findAll());
        return "admin/viewtranslators";
    }


    //attempting a mapping that will allow the user to click on a language and pull back
    //all translators who specialize in that language AGB 1/23
    @RequestMapping(value = "languages")
    //check variable names here
    public String listJobsByLanguages(Model model, @RequestParam String column, @RequestParam String value) {

        Iterable<Language> languages;
        if (column.toLowerCase().equals("all")) {
            languages = languageRepository.findAll();
            model.addAttribute("languages", "All Languages");
        } else {
            languages = Language.findTranslatorByLanguage(value, languageRepository.findAll());
            model.addAttribute("title", "Translators that speak " + columnChoices.get(column) + ":" + value);

        }

        model.addAttribute("Languages", languages);

        return "viewtranslators";//probably not correct. I think I need to create a new html page
    }

}





