package com.kimgreen.backend.domain.member.repository;

import com.kimgreen.backend.domain.community.entity.Post;
import com.kimgreen.backend.domain.member.entity.Member;
import com.kimgreen.backend.domain.member.entity.MemberProfileImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberProfileImgRepository extends JpaRepository<MemberProfileImg, Long> {
    public MemberProfileImg findByMember(Member member);
    public void deleteByMember(Member member);
}
