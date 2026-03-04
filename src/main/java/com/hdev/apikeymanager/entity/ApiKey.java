package com.hdev.apikeymanager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "api_keys",
        indexes = {
                @Index(name = "idx_hashed_key", columnList = "hashedKey")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String hashedKey;

    @Column(nullable = false)
    private int rateLimitPerMinute;

    @Column(nullable = false)
    private int monthlyQuota;

    @Column(nullable = false)
    private int currentMonthUsage;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
