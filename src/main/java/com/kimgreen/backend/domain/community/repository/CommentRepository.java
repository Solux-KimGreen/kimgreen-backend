package com.kimgreen.backend.domain.community.repository;

import com.kimgreen.backend.domain.community.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select count(*) from Comment c join Post p on c.post.postId=p.postId where p.postId= :id")
    public Long countComment(@Param("id") Long postId);
}