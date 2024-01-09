package com.kimgreen.backend.domain.member.repository;

import com.kimgreen.backend.domain.community.entity.Post;
import com.kimgreen.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    public Member findByEmail(String email);
    public boolean existsByEmail(String email);
    public void deleteByEmail(String email);
}
