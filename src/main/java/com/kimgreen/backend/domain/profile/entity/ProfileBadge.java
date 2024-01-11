package com.kimgreen.backend.domain.profile.entity;

import com.kimgreen.backend.domain.AuditEntity;
import com.kimgreen.backend.domain.BadgeList;
import com.kimgreen.backend.domain.member.entity.Member;
import com.kimgreen.backend.exception.BadgeNotFound;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class ProfileBadge extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="profile_badge_id")
    private Long profileBadgeId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private BadgeList profileBadge_1;
    @Enumerated(EnumType.STRING)
    private BadgeList profileBadge_2;
    @Enumerated(EnumType.STRING)
    private BadgeList profileBadge_3;
    @Enumerated(EnumType.STRING)
    private BadgeList profileBadge_4;
    @Enumerated(EnumType.STRING)
    private BadgeList profileBadge_5;

    public void changeProfileBadge(List<BadgeList> badgeList) {
        this.profileBadge_1 = badgeList.get(0);
        this.profileBadge_2 = badgeList.get(1);
        this.profileBadge_3 = badgeList.get(2);
        this.profileBadge_4 = badgeList.get(3);
        this.profileBadge_5 = badgeList.get(4);
    }

}