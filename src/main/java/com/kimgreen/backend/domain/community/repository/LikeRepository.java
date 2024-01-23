package com.kimgreen.backend.domain.community.repository;

import com.kimgreen.backend.domain.community.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Likes, Long> {
    @Query("select count(*) from Likes l join Post p on l.post.postId=p.postId where p.postId= :id")
    public Long countLike(@Param("id") Long postId);
}
