package com.parom.service;

import com.parom.model.User;

import java.util.UUID;

public class UserServiceImpl implements UserService {

    @Override
    public User createUser(
            String firstName,
            String lastName,
            String email,
            String password,
            String repeatPassword
    ) {
        if (firstName == null || firstName.isEmpty()) throw new IllegalArgumentException("Users first name is empty");

        User user = new User(firstName, lastName, email, UUID.randomUUID().toString());
        userRepository.save(user);

        return user;
    }

}
