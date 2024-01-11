package com.kimgreen.backend.domain.profile.repository;

import com.kimgreen.backend.domain.community.entity.Post;
import com.kimgreen.backend.domain.member.entity.Member;
import com.kimgreen.backend.domain.profile.entity.ProfileBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileBadgeRepository extends JpaRepository<ProfileBadge, Long> {
    public void deleteByMember(Member member);
    public ProfileBadge findByMember(Member member);
}