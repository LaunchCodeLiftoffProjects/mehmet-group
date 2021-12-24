package com.translator.hub.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class homeController {

    @GetMapping("/")
    public String home(){
        return ("<h1>Hello, World!e</h1>");

    }

}
