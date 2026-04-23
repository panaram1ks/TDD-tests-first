package com.parom.service;

import com.parom.model.User;

public class EmailVerificationServiceImpl implements EmailVerificationService {
    @Override
    public void scheduleEmailConfirmation(User user) {
        System.out.println("scheduleEmailConfirmation() executed");
        // Put user details into email queue
    }
}
