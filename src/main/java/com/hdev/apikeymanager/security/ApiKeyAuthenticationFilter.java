package com.hdev.apikeymanager.security;

import com.hdev.apikeymanager.entity.ApiKey;
import com.hdev.apikeymanager.entity.ApiUsageLog;
import com.hdev.apikeymanager.repository.ApiKeyRepository;
import com.hdev.apikeymanager.repository.ApiUsageLogRepository;
import com.hdev.apikeymanager.service.ApiKeyService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private final ApiKeyRepository apiKeyRepository;
    private final ApiUsageLogRepository usageRepository;
    private final ApiKeyService apiKeyService;

    /**
     * Filter should run ONLY for API access endpoints
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getRequestURI();

        return path.startsWith("/api/auth")
                || path.startsWith("/api/keys")
                || path.startsWith("/api/health")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui.html");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String apiKeyHeader = request.getHeader("X-API-KEY");

        if (apiKeyHeader == null || apiKeyHeader.isBlank()) {
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

        log.info("API Key ID: {}", apiKey.getId());

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

        log.info("Requests in last minute: {}", requestCount);

        /*
        if (apiKey.getRateLimitPerMinute() > 0 &&
                requestCount >= apiKey.getRateLimitPerMinute()) {

            response.sendError(429, "Rate limit exceeded");
            return;
        }
        */

        if (apiKey.getCurrentMonthUsage() >= apiKey.getMonthlyQuota()) {
            response.sendError(429, "Monthly quota exceeded");
            return;
        }

        // Increment usage safely
        apiKeyService.incrementUsage(apiKey);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        apiKey.getUser().getEmail(),
                        null,
                        Collections.emptyList()
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        ApiUsageLog logEntry = ApiUsageLog.builder()
                .apiKey(apiKey)
                .requestTime(LocalDateTime.now())
                .endpoint(request.getRequestURI())
                .statusCode(200)
                .build();

        usageRepository.save(logEntry);

        filterChain.doFilter(request, response);
    }
}