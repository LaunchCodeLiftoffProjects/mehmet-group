package com.translator.hub.controllers;

import com.translator.hub.data.LangRepository;
import com.translator.hub.data.TranslatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping(value = "List")
public class ListController {

    @Autowired
    private TranslatorRepository translatorRepository;

    @Autowired
    private LangRepository languageRepository;

    static HashMap<String, String> columnChoices = new HashMap<>();

    public ListController () {
        columnChoices.put("all", "All");
        columnChoices.put("translator", "Translator");

    }
}
//This is my second attempt at updating the ListControlller - 1/3/22 4:50