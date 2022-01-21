package com.translator.hub.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("option")
public class OptionController {


    @RequestMapping("")
    public String search(Model model) {
        ArrayList<String> languages = new ArrayList<String>();
        languages.add("german");
        languages.add("french");

        model.addAttribute("languages", languages);
        return "option";
    }


}
