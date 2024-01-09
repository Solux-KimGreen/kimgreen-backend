package com.kimgreen.backend.domain.member.repository;

import com.kimgreen.backend.domain.community.entity.Post;
import com.kimgreen.backend.domain.member.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Ref;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    public boolean existsByEmail(String email);
    public RefreshToken findByEmail(String email);
    public void deleteByEmail(String email);
}
