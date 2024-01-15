package com.kimgreen.backend.domain.notification.entity;

import com.kimgreen.backend.domain.AuditEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Builder
@Getter
public class FCMToken extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="fcm_token_id")
    private Long fcmTokenId;

    @Column(name="receiver_id")
    private String receiverId;

    @Column
    private String fcmToken;

    public void updateToken(String token) {
        this.fcmToken = token;
    }

}
