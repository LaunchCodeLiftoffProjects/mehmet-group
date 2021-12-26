package com.translator.hub.models;

import org.hibernate.annotations.GenericGenerator;

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

    @Size(max = 500, message = "bio is too long!")
    private String bio;

    @Email(message = "Invalid email. Try again.")
    private String contactEmail;

    @NotBlank(message = "Address can not be blank!")
    private String address;

    private String image;

    public Translator() {
    }
    public Translator(String firstname, String lastname,String address, String email, String bio, String image) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.address = address;
        this.contactEmail = email;
        this.bio = bio;
        this.image = image;
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
        return contactEmail;
    }
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
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
}