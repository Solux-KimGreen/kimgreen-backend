package com.kimgreen.backend.domain.member.dto.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberInfoResponse {
    private String writerEmail;
    private String badge;
    private String badgeImg;
}
