package com.kimgreen.backend.domain.community.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kimgreen.backend.domain.community.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude
public class GetPostInfoResponseDto {
        private String writerNickname;
        private String writerBadge;
        private String writerProfileImg;
        private String imgUrl;
        private String content;
        private Long postId;
        private String category;
        private String tag;
        private Integer likeCount;
        private Integer commentCount;
        private Boolean isLiked;
        private Boolean isMine;
        private String updatedAt;

        public void setImgUrl(String fullUrl) {
                this.imgUrl = fullUrl;
        }
}