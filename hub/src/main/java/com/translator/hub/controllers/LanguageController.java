package com.translator.hub.controllers;

import com.translator.hub.data.LangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("language")
public class LanguageController {
    @Autowired
    private LangRepository langRepository;

}
