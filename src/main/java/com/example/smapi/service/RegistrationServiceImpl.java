package com.example.smapi.service;

import com.example.smapi.model.User;
import com.example.smapi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public RegistrationServiceImpl(UserRepository repository,
                                   PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public void register(User user) {
        String username = user.getUsername();
        String email = user.getEmail();

        if (repository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Name: " + username + " is already taken.");
        }
        if (repository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User with email: " + email + " already exists.");
        }

        user.setPassword(encoder.encode(user.getPassword()));

        repository.save(user);
    }
}
