package com.hdev.apikeymanager.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CreateApiKeyRequest {
    @Min(1)
    private int rateLimitPerMinute;

    @Min(1)
    private int monthlyQuota;

    private int validityDays; // expiry duration
}
