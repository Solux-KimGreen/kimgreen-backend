package com.kimgreen.backend.domain.community.service;

import com.kimgreen.backend.domain.community.dto.report.ReportRequestDto;
import com.kimgreen.backend.domain.community.entity.Post;
import com.kimgreen.backend.domain.community.repository.PostRepository;
import com.kimgreen.backend.domain.community.repository.ReportRepository;
import com.kimgreen.backend.domain.member.entity.Member;
import com.kimgreen.backend.domain.member.service.MemberService;
import com.kimgreen.backend.exception.PostNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final MemberService memberService;
    private final PostRepository postRepository;

    public void postReport(Long postId,ReportRequestDto reportRequestDto) {
        Member member = memberService.getCurrentMember();
        Post post = postRepository.findById(postId).orElseThrow(PostNotFound::new);
        reportRepository.save(
                reportRequestDto.toEntity(
                        reportRequestDto.getReason(), reportRequestDto.getContent(), member,post
                )
        );
    }
}