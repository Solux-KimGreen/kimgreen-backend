package com.kimgreen.backend.domain.profile.dto.Calendar;

import com.kimgreen.backend.domain.community.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CalendarDetailDto {
    private Long postId;
    private String writerNickname;
    private String profileImg;
    private String writerBadge;
    private String content;
    private int likeCount;
    private Long commentCount;
    private String imgUrl;
    private Boolean isLiked;

}