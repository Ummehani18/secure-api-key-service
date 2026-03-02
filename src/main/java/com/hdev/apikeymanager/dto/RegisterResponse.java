package com.hdev.apikeymanager.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class RegisterResponse {
    private Long id;
    private String email;
    private String role;

}
