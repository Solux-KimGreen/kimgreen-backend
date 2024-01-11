package com.kimgreen.backend.domain.profile.service;

import com.kimgreen.backend.domain.member.entity.Member;
import com.kimgreen.backend.domain.member.service.MemberService;
import com.kimgreen.backend.domain.profile.dto.RepBadgeRequestDto;
import com.kimgreen.backend.domain.profile.entity.RepresentativeBadge;
import com.kimgreen.backend.domain.profile.repository.BadgeRepository;
import com.kimgreen.backend.domain.profile.repository.ProfileBadgeRepository;
import com.kimgreen.backend.domain.profile.repository.RepresentativeBadgeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;
    private final ProfileBadgeRepository profileBadgeRepository;
    private final RepresentativeBadgeRepository representativeBadgeRepository;
    private final MemberService memberService;

    @Transactional
    public void changeRepBadge(RepBadgeRequestDto repBadgeRequestDto) {
        Member member =  memberService.getCurrentMember();
        RepresentativeBadge representativeBadge = representativeBadgeRepository.findByMember(member);
        representativeBadge.changeRepBadge(repBadgeRequestDto.getBadgeName());
    }
}
