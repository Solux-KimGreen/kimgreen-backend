package com.kimgreen.backend.domain.profile.service;

import com.kimgreen.backend.domain.BadgeList;
import com.kimgreen.backend.domain.community.service.S3Service;
import com.kimgreen.backend.domain.member.entity.Member;
import com.kimgreen.backend.domain.member.entity.MemberProfileImg;
import com.kimgreen.backend.domain.member.repository.MemberProfileImgRepository;
import com.kimgreen.backend.domain.member.repository.MemberRepository;
import com.kimgreen.backend.domain.member.service.MemberService;
import com.kimgreen.backend.domain.profile.dto.GetProfileDto;
import com.kimgreen.backend.domain.profile.entity.ProfileBadge;
import com.kimgreen.backend.domain.profile.entity.RepresentativeBadge;
import com.kimgreen.backend.domain.profile.repository.ProfileBadgeRepository;
import com.kimgreen.backend.domain.profile.repository.RepresentativeBadgeRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
@RequiredArgsConstructor

public class ProfileService {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final GetProfileDto getProfileDto;
    private final MemberProfileImgRepository memberProfileImgRepository;
    private final S3Service s3Service;
    private final RepresentativeBadgeRepository representativeBadgeRepository;
    private final ProfileBadgeRepository profileBadgeRepository;

    public void getProfilePosts(){

    }
    public GetProfileDto getProfileInfo(@RequestParam("memberId") Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(); // 찾고싶은 멤버
        MemberProfileImg memberProfileImg = memberProfileImgRepository.findByMember(member);
        RepresentativeBadge representativeBadge = representativeBadgeRepository.findByMember(member);
        profileBadgeRepository.findByMember(member);

        ArrayList<String> badgeList = new ArrayList<>();
        ArrayList<String> badgeImgList = new ArrayList<>();

        ProfileBadge profileBadge = profileBadgeRepository.findByMember(member);
        ArrayList<BadgeList> list = new ArrayList<>();
        list.add(profileBadge.getProfileBadge_1());
        list.add(profileBadge.getProfileBadge_2());
        list.add(profileBadge.getProfileBadge_3());
        list.add(profileBadge.getProfileBadge_4());
        list.add(profileBadge.getProfileBadge_5());
        for(BadgeList b : list){
            badgeList.add(b.name);
            if(b != BadgeList.BLANK){
                badgeImgList.add(s3Service.getFullUrl(b.url));
            }
        }

        return getProfileDto.from(member,
                s3Service.getFullUrl(memberProfileImg.getImgUrl()),
                representativeBadge.getRepresentativeBadge().name,
                s3Service.getFullUrl(representativeBadge.getRepresentativeBadge().url),
                badgeList,
                badgeImgList,
                memberId.equals(memberService.getCurrentMember().getMemberId())
                );
    }
}
