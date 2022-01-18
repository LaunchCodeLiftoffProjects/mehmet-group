package com.translator.hub.service;

import com.translator.hub.models.DTO.UserForm;
import com.translator.hub.models.User;

public interface UserService {

    public User save(UserForm userForm) throws EmailExistsException;
    public User findByEmail(String email);

}
