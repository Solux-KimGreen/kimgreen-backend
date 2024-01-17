package com.kimgreen.backend.domain.member.dto.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SettingInfoResponseDto {
    private String nickname;
    private String profileImg;
    private String profileBadge;
    private String profileBadgeImg;
    private boolean commentAgreement;
}
