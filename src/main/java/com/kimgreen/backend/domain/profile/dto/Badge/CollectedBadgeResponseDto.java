package com.kimgreen.backend.domain.profile.dto.Badge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectedBadgeResponseDto {
    private String badge;
    private String badgeImg;
}
