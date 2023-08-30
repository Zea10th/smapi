package com.example.smapi.service;

import com.example.smapi.model.User;
import org.springframework.stereotype.Service;

public interface RegistrationService {
    void register(User user);
}
