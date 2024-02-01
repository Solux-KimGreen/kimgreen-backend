package com.kimgreen.backend.domain.profile.dto.Profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetSettingPostDto {
    private Long postId;
    private String writerNickname;
    private String writerBadge;
    private String writerProfileImg;
    private String content;
    private Long likeCount;
    private Long commentCount;
    private String imgUrl;
    private boolean isLiked;

    public static GetSettingPostDto toDto(Long postId, String content, String writerBadge, String writerNickname, String writerProfileImg, Long likeCount, Long commentCount, String imgUrl) {
        return GetSettingPostDto.builder()
                .postId(postId)
                .content(content)
                .writerBadge(writerBadge)
                .writerNickname(writerNickname)
                .writerProfileImg(writerProfileImg)
                .likeCount(likeCount)
                .commentCount(commentCount)
                .imgUrl(imgUrl)
                .build();
    }
    public static GetSettingPostDto toDto(Long postId, String content, String writerBadge, String writerNickname, String writerProfileImg, Long likeCount, Long commentCount) {
        return GetSettingPostDto.builder()
                .postId(postId)
                .content(content)
                .writerBadge(writerBadge)
                .writerNickname(writerNickname)
                .writerProfileImg(writerProfileImg)
                .likeCount(likeCount)
                .commentCount(commentCount)
                .build();
    }
}
