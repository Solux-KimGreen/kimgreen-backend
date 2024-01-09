package com.kimgreen.backend.domain.community.repository;

import com.kimgreen.backend.domain.community.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Likes, Long> {
}
