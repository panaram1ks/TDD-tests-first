package com.parom.service;

import com.parom.model.User;

public interface EmailVerificationService {
    void scheduleEmailConfirmation(User user);
}
