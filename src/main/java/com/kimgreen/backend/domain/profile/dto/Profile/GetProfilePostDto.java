package com.kimgreen.backend.domain.profile.dto.Profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class GetProfilePostDto {
    private Long postId;
    private String writerNickname;
    private String writerBadge;
    private String writerProfileImg;
    private String content;
    private int likeCount;
    private int commentCount;
    private String imgUrl;
    private boolean isLiked;
}
