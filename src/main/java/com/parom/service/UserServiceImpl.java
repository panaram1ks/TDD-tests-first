package com.parom.service;

import com.parom.data.UserRepository;
import com.parom.model.User;
import com.parom.service.exception.EmailNotificationServiceException;
import com.parom.service.exception.UserServiceException;

import java.util.UUID;

public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    EmailVerificationService emailVerificationService;

    public UserServiceImpl(UserRepository userRepository, EmailVerificationService emailVerificationService) {
        this.userRepository = userRepository;
        this.emailVerificationService = emailVerificationService;
    }

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

//        UserRepository userRepository = new UserRepositoryImpl(); // Real object (for integration test)! but we want Mock object
        boolean isUserCreated;
        try {
            isUserCreated = userRepository.save(user);
        } catch (RuntimeException ex) {
            throw new UserServiceException(ex.getMessage());
        }
        if (!isUserCreated) throw new UserServiceException("Could not create user");
        try {
            emailVerificationService.scheduleEmailConfirmation(user);
        } catch (RuntimeException e) {
            throw new EmailNotificationServiceException(e.getMessage());
        }
        return user;
    }

    public void demo() {
        System.out.println("Demo method");
    }

}
