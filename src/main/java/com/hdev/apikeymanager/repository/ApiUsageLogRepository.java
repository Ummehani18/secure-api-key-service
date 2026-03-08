package com.hdev.apikeymanager.repository;

import com.hdev.apikeymanager.entity.ApiUsageLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ApiUsageLogRepository extends JpaRepository<ApiUsageLog, Long> {

    long countByApiKeyIdAndRequestTimeAfter(Long apiKeyId, LocalDateTime time);
}