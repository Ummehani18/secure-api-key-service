package com.hdev.apikeymanager.repository;

import com.hdev.apikeymanager.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

    List<ApiKey> findByUserEmail(String email);

    @Query("SELECT ak FROM ApiKey ak JOIN FETCH ak.user WHERE ak.hashedKey = :hashedKey")
    Optional<ApiKey> findByHashedKey(@Param("hashedKey") String hashedKey);
}
