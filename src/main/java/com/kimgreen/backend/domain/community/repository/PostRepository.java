package com.kimgreen.backend.domain.community.repository;

import com.kimgreen.backend.domain.community.entity.Category;
import com.kimgreen.backend.domain.community.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    int countByCategory(Category category); //해당 카테고리에 속한 게시글 수 조회
}
