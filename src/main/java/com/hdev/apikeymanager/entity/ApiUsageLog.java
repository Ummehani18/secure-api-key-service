package com.hdev.apikeymanager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "api_usage_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiUsageLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime requestTime;

    private String endpoint;

    private int statusCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "api_key_id")
    private ApiKey apiKey;
}
