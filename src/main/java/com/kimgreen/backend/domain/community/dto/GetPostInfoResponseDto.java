package com.kimgreen.backend.domain.community.dto;

import com.kimgreen.backend.domain.community.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPostInfoResponseDto {
        private String writerNickname;
        private String writerBadge;
        private String content;
        private Integer postId;
        private String category;
        private Integer likeCount;
        private Integer commentCount;
        private Boolean isLiked;
        private Boolean isMine;
        private String updatedAt;

        public static GetPostInfoResponseDto from(Post post, Boolean isWriter, Boolean liked, String badge){
                return GetPostInfoResponseDto.builder()
                        .writerNickname(post.getMember().getNickname())
                        .writerBadge(badge)
                        .content(post.getContent())
                        .category(String.valueOf(post.getCategory()))
                        .likeCount(post.getLikes().size())
                        .commentCount(post.getComments().size())
                        .isLiked(liked)
                        .isMine(isWriter)
                        .updatedAt(String.valueOf(post.getModifiedAt()))
                        .build();
        }
    }