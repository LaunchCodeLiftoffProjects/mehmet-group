package com.translator.hub.controllers;

import com.translator.hub.data.LangRepository;
import com.translator.hub.data.TestimonialRepository;
import com.translator.hub.data.TranslatorRepository;
import com.translator.hub.data.UserRepository;
import com.translator.hub.models.Language;
import com.translator.hub.models.Testimonial;
import com.translator.hub.models.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class HomeController {
    //connect to repositories
    @Autowired
    private LangRepository langRepository;

    @Autowired
    private TranslatorRepository translatorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestimonialRepository testimonialRepository;

    private List<Translator> translators;

//agb try changing this chunk of code 1/5
//    @RequestMapping("")
//    public String index(Model model) {
//        model.addAttribute("title", "Translators");
//        return "index";
  //  }

//Added lines 40-44
    @RequestMapping("/")
    public String index(Model model) {

        model.addAttribute("translators", translatorRepository.findAll());
        if (testimonialRepository.count() > 0) {
            List<Testimonial> approvedTestimonials = testimonialRepository.findByApprovedTrue();
            int random = (int)(Math.random() * approvedTestimonials.size());
            model.addAttribute("testimonial", approvedTestimonials.get(random));
        }

        return "index";
      }


    //add translators to repository using registration form
    @GetMapping("add")
    public String displayTranslatorRegForm(Model model) {
        model.addAttribute(new Translator());

        //model.addAttribute("translator", translatorRepository);
        //model.addAttribute("language", langRepository);
        //changing lines 52 and 53 AGB 1/5/22
        model.addAttribute("translators", translatorRepository.findAll());
        model.addAttribute("languages", langRepository.findAll());

        return "add";
    }

    //** Anita to return individual translators 1/5 lines 63-71
// view translator by ID
 @GetMapping("view/{translatorId}")
 public String displayViewTranslator(Model model, @PathVariable int translatorId) {
       return "view";
    }

    @GetMapping
    public String index() {
        return "index";
    }



// I don't think we need this. Adds translators as a form  -Lindsey
    @PostMapping("add")
    public String processTranslatorForm(@ModelAttribute @Valid Translator newTranslator, Errors errors, Model model, @RequestParam int translatorId, @RequestParam List<Integer>language) {
        if (errors.hasErrors()) {

            return "add";
        }

//        Searches for Translators by language
        List<Language> langObjs = (List<Language>) langRepository.findAllById(language);

//        Create a new translator if not finding one by ID or
        Translator translator = translatorRepository.findById(translatorId).orElse(new Translator());
        newTranslator.setTranslator(translator);

        return "redirect:";

    }

    }
