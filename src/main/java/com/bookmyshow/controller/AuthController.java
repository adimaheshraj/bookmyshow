package com.bookmyshow.controller;

import com.bookmyshow.dto.AuthRequest;
import com.bookmyshow.dto.AuthResponse;
import com.bookmyshow.model.User;
import com.bookmyshow.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return userService.login(request);
    }
}
