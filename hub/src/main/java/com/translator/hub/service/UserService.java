package com.translator.hub.service;

import com.translator.hub.models.User;

public interface UserService {

    public User findUserByEmail(String email);

    public void saveUser(User user);

}
