package com.hdev.apikeymanager.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateApiKeyResponse {
    private String apiKey;
    private int rateLimitPerMinute;
    private int monthlyQuota;
    private String expiryDate;
}
