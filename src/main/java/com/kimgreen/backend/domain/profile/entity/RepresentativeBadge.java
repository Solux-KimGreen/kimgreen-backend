package com.kimgreen.backend.domain.profile.entity;

import com.fasterxml.jackson.databind.ser.std.EnumSerializer;
import com.kimgreen.backend.domain.AuditEntity;
import com.kimgreen.backend.domain.BadgeList;
import com.kimgreen.backend.domain.member.entity.Member;
import com.kimgreen.backend.exception.BadgeNotFound;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class RepresentativeBadge extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="representative_badge_id")
    private Long representativeBadgeId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private BadgeList representativeBadge;

    public void changeRepBadge(String badge) {
        if(Arrays.stream(BadgeList.values()).anyMatch(v->v.name().equals(badge))) {
            this.representativeBadge = BadgeList.valueOf(badge);
        } else throw new BadgeNotFound();
    }


}
