package com.translator.hub.models;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.*;


@Entity
@Table(name = "translator")
public class Translator {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "native")
    @GenericGenerator(
            name = "native",
            strategy = "native")
    private int id;

    @NotBlank(message = "First Name is required.")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last Name is required.")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String lastName;

    @NotBlank(message = "Language is required.")
    @Size(min = 3, max = 50, message = "Language must be between 3 and 50 characters")
    private String language;

    @Size(max = 500, message = "bio is too long!")
    private String bio;

    @NotBlank(message = "Address can not be blank!")
    private String address;

    private String image;

    @Email(message = "Invalid email. Try again.")
    private String email;

    @NotNull
    private String pwHash;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Translator() {
    }
    public Translator(String firstname, String lastname,String language, String address, String bio, String image, String email, String password) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.language = language;
        this.address = address;
        this.email = email;
        this.bio = bio;
        this.image = image;
        this.pwHash = encoder.encode(password);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public String getContactEmail() {
        return email;
    }
    public void setContactEmail(String contactEmail) {
        this.email = contactEmail;
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
}