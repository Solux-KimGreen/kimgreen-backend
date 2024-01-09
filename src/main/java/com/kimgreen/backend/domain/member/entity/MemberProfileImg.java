package com.kimgreen.backend.domain.member.entity;

import com.kimgreen.backend.domain.AuditEntity;
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
public class MemberProfileImg extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="profile_img_id")
    private long profileImgId;

    @OneToOne
    @JoinColumn(name="member_id")
    private Member member;

    //기본이미지 url 넣기
    @Column(name="img_url")
    private String imgUrl;
}
