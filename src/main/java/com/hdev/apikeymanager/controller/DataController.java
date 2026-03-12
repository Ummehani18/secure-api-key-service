package com.hdev.apikeymanager.controller;

import com.hdev.apikeymanager.dto.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.hdev.apikeymanager.dto.ApiResponse;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/data")
@Tag(name = "Protected APIs")
public class DataController {

    @GetMapping
    public ApiResponse<String> getData() {

        return ApiResponse.<String>builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.OK.value())
                .message("Protected data retrieved successfully")
                .data("Protected API accessed successfully")
                .build();
    }
}