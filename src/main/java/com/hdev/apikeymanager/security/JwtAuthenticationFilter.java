package com.hdev.apikeymanager.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        System.out.println("Header received: " + header);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            System.out.println("Token: " + token);
            System.out.println("Is token valid? " + jwtUtil.isTokenValid(token));

            if (jwtUtil.isTokenValid(token)) {
                String email = jwtUtil.extractEmail(token);
                System.out.println("Extracted email: " + email);

                var auth = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        email, null, java.util.Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
