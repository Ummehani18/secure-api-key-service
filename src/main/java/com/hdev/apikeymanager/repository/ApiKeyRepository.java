package com.hdev.apikeymanager.repository;

import com.hdev.apikeymanager.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    Optional<ApiKey> findByHashedKey(String hashedKey);
    List<ApiKey> findByUserEmail(String email);
}
