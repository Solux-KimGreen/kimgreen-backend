package com.kimgreen.backend.domain.community.dto;

import com.kimgreen.backend.domain.community.entity.Category;
import com.kimgreen.backend.domain.community.entity.Post;
import com.kimgreen.backend.domain.community.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WritePostRequestDto {
    private String category;
    private String content;

    public Post toPostEntity(String category, String content) {
        return Post.builder()
                .category(Category.valueOf(category))
                .content(content)
                .tag(Tag.DAILY)
                .build();
    }
}