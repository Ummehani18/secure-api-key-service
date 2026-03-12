package com.hdev.apikeymanager.controller;

import com.hdev.apikeymanager.dto.*;
import com.hdev.apikeymanager.service.ApiKeyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.hdev.apikeymanager.dto.ApiResponse;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/keys")
@RequiredArgsConstructor
@Tag(name = "API Key Management", description = "Create, list and revoke API keys")

public class ApiKeyController {

    private final ApiKeyService apiKeyService;


    // CREATE API KEY
    @PostMapping
    public ApiResponse<CreateApiKeyResponse> createKey(
            Authentication authentication,
            @Valid @RequestBody CreateApiKeyRequest request) {

        String email = authentication.getName();

        CreateApiKeyResponse response = apiKeyService.createKey(email, request);

        return ApiResponse.<CreateApiKeyResponse>builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.CREATED.value())
                .message("API key created successfully")
                .data(response)
                .build();
    }


    // LIST USER API KEYS
    @GetMapping
    public ApiResponse<List<ApiKeyResponse>> listKeys(
            @AuthenticationPrincipal String email) {

        List<ApiKeyResponse> keys = apiKeyService.getUserKeys(email);

        return ApiResponse.<List<ApiKeyResponse>>builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.OK.value())
                .message("API keys retrieved successfully")
                .data(keys)
                .build();
    }


    // REVOKE API KEY
    @DeleteMapping("/{id}")
    public ApiResponse<String> revokeKey(
            @PathVariable Long id,
            @AuthenticationPrincipal String email) {

        apiKeyService.revokeKey(id, email);

        return ApiResponse.<String>builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.OK.value())
                .message("API key revoked successfully")
                .data(null)
                .build();
    }
}