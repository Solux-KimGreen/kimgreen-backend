package com.kimgreen.backend.domain.community.dto;

import com.kimgreen.backend.domain.community.entity.Category;
import com.kimgreen.backend.domain.community.entity.Post;
import com.kimgreen.backend.domain.community.entity.Tag;
import com.kimgreen.backend.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WritePostRequestDto {
    private String category;
    private String content;

    public Post toCertifyPostEntity(String category, String content, Member member) {
        return Post.builder()
                .member(member)
                .category(Category.valueOf(category))
                .content(content)
                .tag(Tag.CERTIFY)
                .build();
    }

    public Post toDailyPostEntity(String category, String content, Member member) {
        return Post.builder()
                .member(member)
                .category(Category.valueOf(category))
                .content(content)
                .tag(Tag.DAILY)
                .build();
    }
}