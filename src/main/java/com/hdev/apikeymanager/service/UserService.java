package com.hdev.apikeymanager.service;

import com.hdev.apikeymanager.dto.LoginRequest;
import com.hdev.apikeymanager.dto.LoginResponse;
import com.hdev.apikeymanager.dto.RegisterRequest;
import com.hdev.apikeymanager.dto.RegisterResponse;
import com.hdev.apikeymanager.entity.User;
import com.hdev.apikeymanager.repository.UserRepository;
import com.hdev.apikeymanager.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .createdAt(LocalDateTime.now())
                .build();

        User saved = userRepository.save(user);

        return RegisterResponse.builder()
                .id(saved.getId())
                .email(saved.getEmail())
                .role(saved.getRole())
                .build();
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return LoginResponse.builder()
                .token(token)
                .build();
    }

}
