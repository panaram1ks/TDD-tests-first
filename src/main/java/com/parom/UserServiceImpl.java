package com.parom;

import com.parom.model.User;

public class UserServiceImpl implements UserService {

    @Override
    public User createUser(
            String firstName,
            String lastName,
            String email,
            String password,
            String repeatPassword
    ) {
        return new User(firstName, lastName, email);
    }

}
