package com.hdev.apikeymanager.controller;

import com.hdev.apikeymanager.dto.ApiKeyResponse;
import com.hdev.apikeymanager.dto.CreateApiKeyRequest;
import com.hdev.apikeymanager.dto.CreateApiKeyResponse;
import com.hdev.apikeymanager.service.ApiKeyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/keys")
@RequiredArgsConstructor
public class ApiKeyController {

    private final ApiKeyService apiKeyService;

    @PostMapping
    public CreateApiKeyResponse createKey(Authentication authentication, @Valid @RequestBody CreateApiKeyRequest request) {

        String email = authentication.getName();

        return apiKeyService.createKey(email, request);
    }

    @GetMapping
    public List<ApiKeyResponse> listKeys(@AuthenticationPrincipal String email) {
        return apiKeyService.getUserKeys(email);
    }

    @DeleteMapping("/{id}")
    public String revokeKey(
            @PathVariable Long id,
            @AuthenticationPrincipal String email) {

        apiKeyService.revokeKey(id, email);

        return "API key revoked successfully";
    }
}