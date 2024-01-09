package com.kimgreen.backend.domain.member.entity;

import com.kimgreen.backend.domain.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="refresh_token_id")
    private Long refreshTokenId;

    @Column(name="refresh_token", nullable = false)
    private String refreshToken;

    @Column(name="email", nullable = false)
    private String email;


    public void updateRefreshToken(String token) {
        this.refreshToken = token;
    }

}
