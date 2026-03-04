package com.hdev.apikeymanager.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

public class LoginResponse {
    private String token;
}
