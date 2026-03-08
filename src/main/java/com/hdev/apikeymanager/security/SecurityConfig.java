package com.hdev.apikeymanager.security;

import com.hdev.apikeymanager.repository.ApiKeyRepository;
import com.hdev.apikeymanager.repository.ApiUsageLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final ApiKeyRepository apiKeyRepository;
    private final ApiUsageLogRepository usageRepository;
    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        JwtAuthenticationFilter jwtFilter =
                new JwtAuthenticationFilter(jwtUtil);

        ApiKeyAuthenticationFilter apiKeyFilter =
                new ApiKeyAuthenticationFilter(apiKeyRepository, usageRepository);

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/health").permitAll()
                        .anyRequest().permitAll()
                )

                // JWT must run first
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                // API key validation runs after JWT
                .addFilterAfter(apiKeyFilter, JwtAuthenticationFilter.class);

        return http.build();
    }
}