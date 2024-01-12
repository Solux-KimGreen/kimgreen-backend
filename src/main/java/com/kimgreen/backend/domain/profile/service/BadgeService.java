package com.kimgreen.backend.domain.profile.service;

import com.kimgreen.backend.domain.BadgeList;
import com.kimgreen.backend.domain.member.entity.Member;
import com.kimgreen.backend.domain.member.service.MemberService;
import com.kimgreen.backend.domain.profile.dto.Badge.ProfileBadgeRequestDto;
import com.kimgreen.backend.domain.profile.dto.Badge.RepBadgeRequestDto;
import com.kimgreen.backend.domain.profile.entity.ProfileBadge;
import com.kimgreen.backend.domain.profile.entity.RepresentativeBadge;
import com.kimgreen.backend.domain.profile.repository.BadgeRepository;
import com.kimgreen.backend.domain.profile.repository.ProfileBadgeRepository;
import com.kimgreen.backend.domain.profile.repository.RepresentativeBadgeRepository;
import com.kimgreen.backend.exception.BadgeNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Transactional
    public void changeProfileBadge(ProfileBadgeRequestDto profileBadgeRequestDto){
        Member member=memberService.getCurrentMember();
        ProfileBadge profileBadge = profileBadgeRepository.findByMember(member);
        //뱃지 이름 검증
        List<String> badges = profileBadgeRequestDto.getBadge();
        validateList(badges);
        //List<String> -> List<BadgeList>
        List<BadgeList> badgeList = toEnumList(badges);

        profileBadge.changeProfileBadge(badgeList);
    }


    public List<BadgeList> toEnumList(List<String> badgeList) {
        List<BadgeList> returnList = new ArrayList<>();
        for(String badge: badgeList) {
            returnList.add(Arrays.stream(BadgeList.values())
                            .filter(v->v.name().equals(badge))
                            .findAny()
                            .orElseThrow());
        }
        return returnList;

    }

    public void validateList(List<String> badges) {
        for(String badge: badges) {
            if (!(Arrays.stream(BadgeList.values()).anyMatch(v -> v.name().equals(badge)))) {
                throw new BadgeNotFound();
            }
        }
    }
}
