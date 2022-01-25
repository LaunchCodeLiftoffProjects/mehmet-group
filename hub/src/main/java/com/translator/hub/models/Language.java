package com.translator.hub.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@Entity
@Table(name = "languages")
public class Language {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "native")
    @GenericGenerator(
            name = "native",
            strategy = "native")
    private int id;

    @NotBlank(message = "Language Name is required.")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    public Language() {
    }

    public Language(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //attempting to write a method to send the languages to the ListController
    //AGB 1/25

    public static ArrayList<Language> findTranslatorByLanguage(String value, Iterable<Language> allLanguages) {
        String lower_val = value.toLowerCase();
        ArrayList<Language> results = new ArrayList<>();

        for (Language language : allLanguages) {
            if (language.getName().toLowerCase().contains(lower_val)) {
                results.add(language);
            }
            //may have to check something with the ID here

        }
        return results;
    }
}