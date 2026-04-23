package com.parom.service;

import com.parom.model.User;

public class EmailVerificationServiceImpl implements EmailVerificationService {
    @Override
    public void scheduleEmailConfirmation(User user) {
        // Put user details into email queue
    }
}
