package com.tutor.journal.controller;

import com.tutor.journal.entity.User;
import com.tutor.journal.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        user.setRole("ROLE_STUDENT");
        return userRepository.save(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User loginData) {
        return userRepository.findByEmail(loginData.getEmail())
                .filter(u -> u.getPassword().equals(loginData.getPassword()))
                .orElseThrow(() -> new RuntimeException("Невірний email або пароль"));
    }
}
