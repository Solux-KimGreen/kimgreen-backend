package com.kimgreen.backend.domain.profile.repository;

import com.kimgreen.backend.domain.member.entity.Member;
import com.kimgreen.backend.domain.profile.entity.ProfileBadge;
import com.kimgreen.backend.domain.profile.entity.RepresentativeBadge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepresentativeBadgeRepository extends JpaRepository<RepresentativeBadge, Long> {
    public RepresentativeBadge findByMember(Member member);
    public void deleteByMember(Member member);
}