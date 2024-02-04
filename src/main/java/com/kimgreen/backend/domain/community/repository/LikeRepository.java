package com.kimgreen.backend.domain.community.repository;

import com.kimgreen.backend.domain.community.entity.Likes;
import com.kimgreen.backend.domain.community.entity.Post;
import com.kimgreen.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Likes, Long> {
    @Query("select count(*) from Likes l join Post p on l.post.postId=p.postId where p.postId= :id")
    public Long countLike(@Param("id") Long postId);

    public Optional<Likes> findByPostAndMember(Post post, Member member);
}