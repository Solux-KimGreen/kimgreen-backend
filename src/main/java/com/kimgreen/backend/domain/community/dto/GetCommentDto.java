package com.kimgreen.backend.domain.community.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kimgreen.backend.domain.community.entity.Comment;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class GetCommentDto {
    private Long commentId;
    private String writerProfileImg;
    private String writerNickname;
    private String writerBadge;
    private String content;
    private boolean isWriter;
    private LocalDateTime createAt;

    public static GetCommentDto from(Comment comment, String writerProfileImg, String writerBadge, boolean isWriter) {

        return GetCommentDto.builder()
                .commentId(comment.getCommentId())
                .writerProfileImg(writerProfileImg)
                .writerNickname(comment.getMember().getNickname())
                .writerBadge(writerBadge)
                .content(comment.getContent())
                .isWriter(isWriter)
                .createAt(comment.getCreatedAt())
                .build();
    }

}