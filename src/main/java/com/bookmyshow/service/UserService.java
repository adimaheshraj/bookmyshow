package com.bookmyshow.service;

import com.bookmyshow.dto.AuthRequest;
import com.bookmyshow.dto.AuthResponse;
import com.bookmyshow.model.User;
import com.bookmyshow.repository.UserRepository;
import com.bookmyshow.config.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired private UserRepository userRepo;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private PasswordEncoder passwordEncoder;

    public ResponseEntity<?> register(User user) {
        // Check for empty email
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email cannot be empty");
        }

        // Check for duplicate email
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        // Check for duplicate username
        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        // Check for empty password
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Password cannot be empty");
        }

        // Encode password and save user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepo.save(user);

        if (savedUser.getId() != null) {
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User not saved");
        }
    }

    public ResponseEntity<AuthResponse> login(AuthRequest request) {
        Optional<User> userOpt = userRepo.findByUsername(request.getUsername());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new AuthResponse("Invalid username or password"));
        }
        User user = userOpt.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new AuthResponse("Invalid username or password"));
        }
        // Optionally, you can add a message or user info in the response
        AuthResponse response = new AuthResponse(jwtUtil.generateToken(user.getUsername()));
        // response.setMessage("Login successful"); // If you want to add a message
        return ResponseEntity.ok(response);
    }
}