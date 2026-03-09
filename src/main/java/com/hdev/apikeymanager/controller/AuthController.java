package com.hdev.apikeymanager.controller;

import com.hdev.apikeymanager.dto.*;
import com.hdev.apikeymanager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        RegisterResponse response = userService.register(request);

        return ApiResponse.<RegisterResponse>builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.CREATED.value())
                .message("User registered successfully")
                .data(response)
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = userService.login(request);

        return ApiResponse.<LoginResponse>builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.OK.value())
                .message("Login successful")
                .data(response)
                .build();
    }
}