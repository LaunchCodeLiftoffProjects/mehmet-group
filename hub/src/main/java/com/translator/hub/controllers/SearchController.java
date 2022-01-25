package com.translator.hub.controllers;


import com.translator.hub.data.LangRepository;
import com.translator.hub.data.TranslatorRepository;
import com.translator.hub.models.Language;
import com.translator.hub.models.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;

@Controller
@RequestMapping("search")
public class SearchController {

    @Autowired
    private TranslatorRepository translatorRepository;

    @Autowired
    private LangRepository langRepository;

    @RequestMapping("")
    public String search(Model model) {
        Iterable<Language> allLanguages = langRepository.findAll();
        model.addAttribute("languages", allLanguages);
        return "search";
    }

  /*  @PostMapping("results")
    public String displaySearchResults(Model model, @RequestParam String language1){
        Iterable<Translator> translators;
        translators = translatorRepository.findAll();

        ArrayList<Translator> results = new ArrayList<>();

        for (Translator translator : translators) {
            if (translator.getLanguage() != null && translator.getLanguage().toLowerCase().contains(language1.toLowerCase())) {
                results.add(translator);
            }
        }

        model.addAttribute("translators", results);


        return "search";
    }*/

    @PostMapping("results")
    public String displaySearchResults(Model model, @RequestParam String language){
        Iterable<Translator> translators;
        translators = translatorRepository.findByLanguageContainsIgnoreCase(language);

        Iterable<Language> allLanguages = langRepository.findAll();
        model.addAttribute("languages", allLanguages);



        model.addAttribute("translators", translators);


        return "search";
    }



}
