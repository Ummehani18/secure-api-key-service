package com.hdev.apikeymanager.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

    private String timestamp;

    private int status;

    private String error;

    private String message;

    private String path;
}