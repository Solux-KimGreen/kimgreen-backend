package com.kimgreen.backend.domain.community.entity;

import com.kimgreen.backend.domain.AuditEntity;
import com.kimgreen.backend.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Report extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="report_id")
    private long reportId;

    //ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id", nullable = false)
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name=" reporter_id", nullable = false)
    private Member member;

    @Column
    private String content;
    @Enumerated(EnumType.STRING)
    private Reason reason;
}
