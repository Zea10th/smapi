package com.example.smapi.service;

import com.example.smapi.model.authentication.AuthRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> login(AuthRequest request);
}
