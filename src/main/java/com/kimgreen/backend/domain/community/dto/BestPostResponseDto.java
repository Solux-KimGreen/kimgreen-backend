package com.kimgreen.backend.domain.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BestPostResponseDto {
    private Long postId;
    private String content;
}
