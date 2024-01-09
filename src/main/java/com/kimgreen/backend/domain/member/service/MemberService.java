package com.kimgreen.backend.domain.member.service;

import com.kimgreen.backend.domain.member.entity.Member;
import com.kimgreen.backend.domain.member.entity.MemberProfileImg;
import com.kimgreen.backend.domain.member.entity.RefreshToken;
import com.kimgreen.backend.domain.member.repository.MemberProfileImgRepository;
import com.kimgreen.backend.domain.member.repository.MemberRepository;
import com.kimgreen.backend.domain.member.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {


    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberProfileImgRepository memberProfileImgRepository;



    public Member getCurrentMember() {
        return memberRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Transactional
    public void changeAlarm(String type) {
        if(type.equals("c")) {
            changeCommentAlarm();
        } else if(type.equals("l")) {
            changeLikeAlarm();
        }
    }

    @Transactional
    public void deleteMember() {
        Member member = getCurrentMember();
        String email = member.getEmail();

        if(refreshTokenRepository.existsByEmail(email)) {
            refreshTokenRepository.deleteByEmail(email);
        }
        memberProfileImgRepository.deleteByMember(member);
        memberRepository.deleteByEmail(email);
    }

    @Transactional
    public void changeCommentAlarm() {
        Member member = getCurrentMember();
        if(member.isCommentAlarm()) {
            member.changeCommentAlarm(false);
        } else {
            member.changeCommentAlarm(true);
        }
    }
    @Transactional
    public void changeLikeAlarm() {
        Member member = getCurrentMember();
        if(member.isLikeAlarm()) {
            member.changeLikeAlarm(false);
        } else {
            member.changeLikeAlarm(true);
        }
    }
}
