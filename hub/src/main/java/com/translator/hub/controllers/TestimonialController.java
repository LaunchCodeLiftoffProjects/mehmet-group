package com.translator.hub.controllers;

import com.translator.hub.data.TestimonialRepository;
import com.translator.hub.models.Language;
import com.translator.hub.models.Testimonial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("testimonial")
public class TestimonialController {

    @Autowired
    private TestimonialRepository testimonialRepository;

    @RequestMapping("")
    public String search() {
        return "testimonial";
    }

    @PostMapping("")
    public String processAddTestimonial(@ModelAttribute @Valid Testimonial newTestimonial,
                                      Errors errors) {

        if (errors.hasErrors()) {
            return "/testimonial";
        }

        testimonialRepository.save(newTestimonial);
        return "index";
    }



}
