package com.hdev.apikeymanager.controller;

import com.hdev.apikeymanager.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping
    public ApiResponse<String> healthCheck() {

        return ApiResponse.<String>builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.OK.value())
                .message("Service is healthy")
                .data("API Key Manager is running")
                .build();
    }
}