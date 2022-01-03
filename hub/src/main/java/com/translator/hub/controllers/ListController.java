package com.translator.hub.controllers;

import com.translator.hub.data.TranslatorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping(value = "List")
public class ListController {

    @Autowired
    private TranslatorRepository translatorRepository;
}
//This is my second attempt at updating the ListControlller - 1/3/22 4:50