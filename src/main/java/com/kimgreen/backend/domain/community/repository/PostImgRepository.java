package com.kimgreen.backend.domain.community.repository;

import com.kimgreen.backend.domain.community.entity.Post;
import com.kimgreen.backend.domain.community.entity.PostImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImgRepository extends JpaRepository<PostImg, Long> {
    public PostImg findByPost(Post post);
}