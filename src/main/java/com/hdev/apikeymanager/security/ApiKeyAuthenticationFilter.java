package com.hdev.apikeymanager.security;

import com.hdev.apikeymanager.entity.ApiKey;
import com.hdev.apikeymanager.entity.ApiUsageLog;
import com.hdev.apikeymanager.repository.ApiKeyRepository;
import com.hdev.apikeymanager.repository.ApiUsageLogRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private final ApiKeyRepository apiKeyRepository;
    private final ApiUsageLogRepository usageRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getRequestURI();

        return path.startsWith("/api/auth")
                || path.startsWith("/api/keys")
                || path.equals("/health");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String apiKeyHeader = request.getHeader("X-API-KEY");

        if (apiKeyHeader == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "API Key required");
            return;
        }

        String hashedKey = DigestUtils.sha256Hex(apiKeyHeader);

        Optional<ApiKey> optionalKey = apiKeyRepository.findByHashedKey(hashedKey);

        if (optionalKey.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API Key");
            return;
        }

        ApiKey apiKey = optionalKey.get();

        if (!apiKey.isActive()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "API Key revoked");
            return;
        }

        if (apiKey.getExpiryDate().isBefore(LocalDateTime.now())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "API Key expired");
            return;
        }

        long requestCount = usageRepository.countByApiKeyIdAndRequestTimeAfter(
                apiKey.getId(),
                LocalDateTime.now().minusMinutes(1)
        );

        if (apiKey.getRateLimitPerMinute() > 0 &&
                requestCount >= apiKey.getRateLimitPerMinute()) {

            response.sendError(429, "Rate limit exceeded");
            return;
        }

        if (apiKey.getCurrentMonthUsage() >= apiKey.getMonthlyQuota()) {
            response.sendError(429, "Monthly quota exceeded");
            return;
        }

        // 🔴 INCREMENT USAGE HERE
        apiKey.setCurrentMonthUsage(apiKey.getCurrentMonthUsage() + 1);
        apiKeyRepository.save(apiKey);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        apiKey.getUser().getEmail(),
                        null,
                        Collections.emptyList()
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        ApiUsageLog log = ApiUsageLog.builder()
                .apiKey(apiKey)
                .requestTime(LocalDateTime.now())
                .endpoint(request.getRequestURI())
                .statusCode(200)
                .build();

        usageRepository.save(log);

        filterChain.doFilter(request, response);
    }
}

