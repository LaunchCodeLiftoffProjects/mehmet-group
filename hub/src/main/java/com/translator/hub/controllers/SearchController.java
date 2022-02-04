package com.translator.hub.controllers;


import com.translator.hub.data.LangRepository;
import com.translator.hub.data.TranslatorRepository;
import com.translator.hub.models.Language;
import com.translator.hub.models.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

   /* @PostMapping("results")
    public String displaySearchResults(Model model, @RequestParam String language) {
            Iterable<Translator> translators;
            translators = translatorRepository.findByLanguageContainsIgnoreCase(language);

            Iterable<Language> allLanguages = langRepository.findAll();
            model.addAttribute("languages", allLanguages);

            model.addAttribute("translators", translators);

           return "search";
    }*/


    @RequestMapping(value="/results", method = RequestMethod.POST, params={"language"})
    public String displayLanguageSearchResults(Model model, @RequestParam String language){
        Iterable<Translator> translators;
        translators = translatorRepository.findByLanguageContainsIgnoreCase(language);

        Iterable<Language> allLanguages = langRepository.findAll();
        model.addAttribute("languages", allLanguages);


        model.addAttribute("translators", translators);


        return "search";
    }

    @RequestMapping(value="/results", method = RequestMethod.POST, params={"address"})
    public String displayLocationSearchResults(Model model, @RequestParam String address){
        Iterable<Translator> translators;
        translators = translatorRepository.findByAddressIgnoreCase(address);

        Iterable<Language> allLanguages = langRepository.findAll();
        model.addAttribute("languages", allLanguages);


        model.addAttribute("translators", translators);


        return "search";
    }



}
