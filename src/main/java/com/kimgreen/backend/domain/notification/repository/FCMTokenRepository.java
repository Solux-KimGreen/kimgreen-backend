package com.kimgreen.backend.domain.notification.repository;

import com.kimgreen.backend.domain.notification.entity.FCMToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FCMTokenRepository extends JpaRepository<FCMToken,Long> {
    public FCMToken findByReceiverId(String email);
    public boolean existsByReceiverId(String email);
    public void deleteByReceiverId(String email);
}
