package com.hdev.apikeymanager.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiKeyResponse {

    private Long id;

    private int rateLimitPerMinute;

    private int monthlyQuota;

    private int currentMonthUsage;

    private boolean active;

    private String expiryDate;

    private String createdAt;
}