package com.kimgreen.backend.domain.community.dto.report;

import com.kimgreen.backend.domain.community.entity.Post;
import com.kimgreen.backend.domain.community.entity.Reason;
import com.kimgreen.backend.domain.community.entity.Report;
import com.kimgreen.backend.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReportRequestDto {
    private String reason;
    private String content;

    public Report toEntity(String reason, String content, Member member, Post post) {
        return Report.builder()
                .reason(Reason.valueOf(reason))
                .content(content)
                .member(member)
                .post(post)
                .build();
    }
}
