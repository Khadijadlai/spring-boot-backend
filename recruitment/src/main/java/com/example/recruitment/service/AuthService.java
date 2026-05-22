package com.example.recruitment.service;

import com.example.recruitment.dto.AuthResponse;
import com.example.recruitment.dto.LoginRequest;
import com.example.recruitment.dto.RegisterRequest;
import com.example.recruitment.entity.User;
import com.example.recruitment.enums.RoleName;
import com.example.recruitment.repository.UserRepository;
import com.example.recruitment.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    //  REGISTER
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        //  SECURITY FIX: no role from client
        user.setRole(RoleName.CANDIDATE);

        userRepository.save(user);

        //  FIX JWT
        String token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }

    //  LOGIN
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }
}