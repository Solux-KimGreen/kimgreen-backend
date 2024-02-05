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
public class CommentResponseDto {
    private Long commentId;
    private Long postId;
    private String writerBadge;
    private String writerNickname;
    private String comment;

    public static CommentResponseDto toDto(Long commentId, Long postId, String writerBadge, String writerNickname, String comment) {
        return CommentResponseDto.builder()
                .commentId(commentId)
                .postId(postId)
                .writerBadge(writerBadge)
                .writerNickname(writerNickname)
                .comment(comment)
                .build();
    }
}