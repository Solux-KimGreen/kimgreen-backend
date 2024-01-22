package com.kimgreen.backend.domain.profile.service;

import com.kimgreen.backend.domain.member.entity.Member;
import com.kimgreen.backend.domain.member.repository.MemberRepository;
import com.kimgreen.backend.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    public List<Object> getProfileInfo(Long memberId){
        List<Object> profileInfo = new ArrayList<>();
        Member member = memberRepository.findById(memberId).orElseThrow(); // 찾고싶은 멤버

        profileInfo.add(member.getNickname());
        // 뱃지 관련
        profileInfo.add(memberId.equals(memberService.getCurrentMember().getMemberId()));
        return profileInfo;
    }
    /*
    "nickname": "김지은",
		"profileImg": "https://gol2580bucket.s3.us-east-2.amazonaws.com/9d465cf3-b2b0-43f9-ae54-06cfa60c9ebc_.png"
		"profileBadge": "새싹환경운동가",
		"profileBadgeImg": "https://gol2580bucket.s3.us-east-2.amazonaws.com/wizard.png",
		"badgeList": ["새싹환경운동가", "얼리버드","","",""],
		"badgeImgList": ["https://gol2580bucket.s3.us-east-2.amazonaws.com/wizard.png","https://gol2580bucket.s3.us-east-2.amazonaws.com/wizard.png"]
		"isMine": true
     */
}
