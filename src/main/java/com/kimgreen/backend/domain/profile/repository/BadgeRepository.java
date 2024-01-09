package com.kimgreen.backend.domain.profile.repository;

import com.kimgreen.backend.domain.community.entity.Post;
import com.kimgreen.backend.domain.profile.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {
}
