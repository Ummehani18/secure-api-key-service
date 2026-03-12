package com.hdev.apikeymanager.service;

import com.hdev.apikeymanager.dto.ApiKeyResponse;
import com.hdev.apikeymanager.dto.CreateApiKeyRequest;
import com.hdev.apikeymanager.dto.CreateApiKeyResponse;
import com.hdev.apikeymanager.entity.ApiKey;
import com.hdev.apikeymanager.entity.User;
import com.hdev.apikeymanager.repository.ApiKeyRepository;
import com.hdev.apikeymanager.repository.UserRepository;
import com.hdev.apikeymanager.security.ApiKeyGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;
    private final UserRepository userRepository;
    private final ApiKeyGenerator apiKeyGenerator;

    public CreateApiKeyResponse createKey(String userEmail, CreateApiKeyRequest request) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // basic validation
        if (request.getRateLimitPerMinute() <= 0 || request.getMonthlyQuota() <= 0) {
            throw new RuntimeException("Invalid API key configuration");
        }

        String plainKey = apiKeyGenerator.generateKey();
        String hashedKey = DigestUtils.sha256Hex(plainKey);

        ApiKey apiKey = ApiKey.builder()
                .hashedKey(hashedKey)
                .rateLimitPerMinute(request.getRateLimitPerMinute())
                .monthlyQuota(request.getMonthlyQuota())
                .currentMonthUsage(0)
                .active(true)
                .expiryDate(LocalDateTime.now().plusDays(request.getValidityDays()))
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        apiKeyRepository.save(apiKey);

        log.info("API key created for user {}", userEmail);

        return CreateApiKeyResponse.builder()
                .apiKey(plainKey)
                .rateLimitPerMinute(apiKey.getRateLimitPerMinute())
                .monthlyQuota(apiKey.getMonthlyQuota())
                .expiryDate(apiKey.getExpiryDate().toString())
                .build();
    }

    public List<ApiKeyResponse> getUserKeys(String email) {

        List<ApiKey> keys = apiKeyRepository.findByUserEmail(email);

        return keys.stream()
                .map(key -> ApiKeyResponse.builder()
                        .id(key.getId())
                        .rateLimitPerMinute(key.getRateLimitPerMinute())
                        .monthlyQuota(key.getMonthlyQuota())
                        .currentMonthUsage(key.getCurrentMonthUsage())
                        .active(key.isActive())
                        .expiryDate(key.getExpiryDate().toString())
                        .createdAt(key.getCreatedAt().toString())
                        .build())
                .toList();
    }

    public void revokeKey(Long keyId, String email) {

        ApiKey key = apiKeyRepository.findById(keyId)
                .orElseThrow(() -> new RuntimeException("API key not found"));

        if (!key.getUser().getEmail().equals(email)) {
            log.warn("Unauthorized API key revoke attempt by {}", email);
            throw new RuntimeException("You do not own this API key");
        }

        key.setActive(false);
        apiKeyRepository.save(key);

        log.info("API key {} revoked by user {}", keyId, email);
    }

    @Transactional
    public void incrementUsage(ApiKey apiKey) {

        apiKey.setCurrentMonthUsage(apiKey.getCurrentMonthUsage() + 1);

        apiKeyRepository.save(apiKey);

        log.debug("API key {} usage incremented", apiKey.getId());
    }
}