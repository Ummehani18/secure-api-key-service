package com.hdev.apikeymanager.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {

    private String timestamp;

    private int status;

    private String message;

    private T data;
}