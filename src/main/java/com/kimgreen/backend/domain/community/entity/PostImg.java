package com.kimgreen.backend.domain.community.entity;

import com.kimgreen.backend.domain.AuditEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Setter
public class PostImg extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_img_id")
    private long postImgId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post")
    private Post post;

    @Column(name="img_url", nullable = false)
    private String imgUrl;
    @Column(name="title", nullable = false)
    private String title;
}
