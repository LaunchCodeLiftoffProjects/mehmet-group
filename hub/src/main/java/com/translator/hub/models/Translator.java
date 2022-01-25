package com.translator.hub.models;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;


@Entity
@DynamicUpdate
@Table(name = "translator")
public class Translator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    private String firstName;
    private String lastName;
    private String language;
    private String bio;
    private String address;
    private String image;
    private String email;
    private String pwHash;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Transient
    public String getPhotosImagePath() {
        if (image == null || id == 0) {
            return null;
        }
        return "/translator-photos/" + id + "/" + image;
    }



    public Translator() {
    }
    public Translator(String firstname, String lastname, String email, String language, String address, String bio, String password) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.language = language;
        this.address = address;
        this.email = email;
        this.bio = bio;
        this.pwHash = encoder.encode(password);
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

    public void setTranslator(Translator translator) {
    }
}