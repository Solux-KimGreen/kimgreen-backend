package com.kimgreen.backend.domain.community.repository;

        import com.kimgreen.backend.domain.community.entity.Post;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.stereotype.Repository;

        import java.time.LocalDateTime;
        import java.util.List;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
