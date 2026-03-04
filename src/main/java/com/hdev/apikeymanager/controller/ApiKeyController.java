package com.hdev.apikeymanager.controller;

import com.hdev.apikeymanager.dto.CreateApiKeyRequest;
import com.hdev.apikeymanager.dto.CreateApiKeyResponse;
import com.hdev.apikeymanager.service.ApiKeyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/keys")
@RequiredArgsConstructor
public class ApiKeyController {

    private final ApiKeyService apiKeyService;

    @PostMapping
    public CreateApiKeyResponse createKey(
            @AuthenticationPrincipal String email,
            @Valid @RequestBody CreateApiKeyRequest request) {

        return apiKeyService.createKey(email, request);
    }
}