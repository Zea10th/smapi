package com.example.smapi.controller;

import com.example.smapi.model.authentication.AuthRequest;
import com.example.smapi.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        return authService.login(request);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        //TODO Invalidate the current token or perform necessary actions
        return ResponseEntity.ok("Logged out successfully");
    }
}

