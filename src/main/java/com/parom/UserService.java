package com.parom;

import com.parom.model.User;

public interface UserService {


    User createUser(String firstName, String lastName, String email, String password, String repeatPassword);
}
