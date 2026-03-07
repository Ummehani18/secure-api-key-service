package com.hdev.apikeymanager.security;

import com.hdev.apikeymanager.entity.ApiKey;
import com.hdev.apikeymanager.repository.ApiKeyRepository;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Skip auth endpoints
        if (path.startsWith("/api/auth") || path.startsWith("/api/keys")) {
            filterChain.doFilter(request, response);
            return;
        }

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

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        apiKey.getUser().getEmail(),
                        null,
                        Collections.emptyList()
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }



}

