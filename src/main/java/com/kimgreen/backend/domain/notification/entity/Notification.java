package com.kimgreen.backend.domain.notification.entity;

import com.kimgreen.backend.domain.AuditEntity;
import com.kimgreen.backend.domain.community.entity.Comment;
import com.kimgreen.backend.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Builder
@Getter
public class Notification extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="fcm_token_id")
    private Long fcmTokenId;

    @OneToOne
    @JoinColumn(name="member_id")
    private Member member;

    @Column
    private String content;

    @Column(name="post_id")
    private Long postId;
}
