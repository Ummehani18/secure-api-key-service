package com.hdev.apikeymanager.controller;

import com.hdev.apikeymanager.dto.LoginRequest;
import com.hdev.apikeymanager.dto.LoginResponse;
import com.hdev.apikeymanager.dto.RegisterRequest;
import com.hdev.apikeymanager.dto.RegisterResponse;
import com.hdev.apikeymanager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request){
         return userService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }


}
