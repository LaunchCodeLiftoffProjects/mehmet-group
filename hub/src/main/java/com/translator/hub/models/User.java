package com.translator.hub.models;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Entity
public class User extends AbstractEntity {

    @NotBlank
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;


    @NotBlank
    private String password;

    @NotNull
    private Boolean enabled = true;

    public User() {}

    public User(@NotBlank String email, @NotBlank String firstName, @NotBlank String lastName, @NotBlank String password) {

        if (email == null || email.length() == 0 || !isValidEmail(email))
            throw new IllegalArgumentException("Email may not be blank");

        if (firstName == null || firstName.length() == 0)
            throw new IllegalArgumentException("firstName may not be blank");

        if (lastName == null || lastName.length() == 0)
            throw new IllegalArgumentException("lastName may not be blank");

        if (password == null || password.length() == 0)
            throw new IllegalArgumentException("Password may not be blank");

        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public List<String> getRoles() {
        ArrayList<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        return roles;
    }

    public String getEmail() {
        return email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User user = (User) obj;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    private static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("\\S+@\\S+");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}