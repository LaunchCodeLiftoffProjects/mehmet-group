package com.translator.hub.controllers;


import com.translator.hub.data.LangRepository;
import com.translator.hub.models.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("split")
public class SplitController {

    @Autowired
    private LangRepository langRepository;

    @RequestMapping("")
    public String search(Model model) {



        return "split";
    }

    @PostMapping("results")
    public String displaySplitResults(Model model, @RequestParam String language1){

        String[] languages;

        languages = language1.replaceAll(",\\s|\\s", ",").split(",");

        for (String language: languages) {

            Language existingLanguage = langRepository.findByName(language);

            if (existingLanguage == null) {
              Language newLanguage = new Language(language);
              langRepository.save(newLanguage);
            }
        }

        Iterable<Language> allLanguages = langRepository.findAll();


        model.addAttribute("languages", allLanguages);



        return "split";
    }


}
