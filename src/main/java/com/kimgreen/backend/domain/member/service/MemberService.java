package com.kimgreen.backend.domain.member.service;

import com.kimgreen.backend.domain.community.service.S3Service;
import com.kimgreen.backend.domain.member.dto.Auth.DeleteMemberRequestDto;
import com.kimgreen.backend.domain.member.dto.Member.MemberInfoResponse;
import com.kimgreen.backend.domain.member.dto.Member.SettingInfoResponseDto;
import com.kimgreen.backend.domain.member.entity.Member;
import com.kimgreen.backend.domain.member.entity.MemberProfileImg;
import com.kimgreen.backend.domain.member.repository.MemberProfileImgRepository;
import com.kimgreen.backend.domain.member.repository.MemberRepository;
import com.kimgreen.backend.domain.member.repository.RefreshTokenRepository;
import com.kimgreen.backend.domain.notification.repository.FCMTokenRepository;
import com.kimgreen.backend.domain.profile.entity.RepresentativeBadge;
import com.kimgreen.backend.domain.profile.repository.BadgeRepository;
import com.kimgreen.backend.domain.profile.repository.ProfileBadgeRepository;
import com.kimgreen.backend.domain.profile.repository.RepresentativeBadgeRepository;
import com.kimgreen.backend.exception.LogInFailurePassword;
import com.kimgreen.backend.exception.LogInRequiredException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class MemberService {


    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberProfileImgRepository memberProfileImgRepository;
    private final RepresentativeBadgeRepository representativeBadgeRepository;
    private final BadgeRepository badgeRepository;
    private final ProfileBadgeRepository profileBadgeRepository;
    private final S3Service s3Service;
    private final FCMTokenRepository fcmTokenRepository;
    private final PasswordEncoder passwordEncoder;



    public Member getCurrentMember() {

        Member member = memberRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(member==null) {
            throw new LogInRequiredException();
        }
        return member;
    }

    @Transactional
    public void deleteMember(DeleteMemberRequestDto dto) {
        Member member = getCurrentMember();
        validatePassword(dto.getPassword(),member);
        String email = member.getEmail();

        if(refreshTokenRepository.existsByEmail(email)) {
            refreshTokenRepository.deleteByEmail(email);
        }
        if(fcmTokenRepository.existsByReceiverId(email)) {
            fcmTokenRepository.deleteByReceiverId(email);
        }
        profileBadgeRepository.deleteByMember(member);
        badgeRepository.deleteByMember(member);
        representativeBadgeRepository.deleteByMember(member);
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
    /*
    @Transactional
    public void changeLikeAlarm() {
        Member member = getCurrentMember();
        if(member.isLikeAlarm()) {
            member.changeLikeAlarm(false);
        } else {
            member.changeLikeAlarm(true);
        }
    }
     */

    @Transactional
    public void changeNickname(String nickname) {
        Member member = getCurrentMember();
        member.changeNickname(nickname);
    }
    @Transactional
    public void changeProfileImg(MultipartFile multipartFile) throws IOException {
        Member member= getCurrentMember();
        MemberProfileImg memberProfileImg = memberProfileImgRepository.findByMember(member);
        //S3에 업로드
        String newImgUrl = s3Service.saveProfileFile(multipartFile);
        String title = multipartFile.getOriginalFilename();

        // 기존이미지 S3에서 삭제
        deleteFromS3(memberProfileImg.getImgUrl());

        //엔티티 변경
        memberProfileImg.changeProfileImg(newImgUrl,title);
    }

    public SettingInfoResponseDto getSettingInfo() {
        String badgeUrl="";
        Member member = getCurrentMember();

        MemberProfileImg memberProfileImg = memberProfileImgRepository.findByMember(member);
        String profileUrl = s3Service.getFullUrl(memberProfileImg.getImgUrl());

        RepresentativeBadge representativeBadge = representativeBadgeRepository.findByMember(member);
        if(!(representativeBadge.getRepresentativeBadge().name.equals(""))) {
            badgeUrl = s3Service.getFullUrl(representativeBadge.getRepresentativeBadge().url);
        }

        return SettingInfoResponseDto.builder()
                .nickname(member.getNickname())
                .profileImg(profileUrl)
                .profileBadge(representativeBadge.getRepresentativeBadge().name)
                .profileBadgeImg(badgeUrl)
                .commentAgreement(member.isCommentAlarm())
                .build();
    }

    public MemberInfoResponse getMemberInfo() {
        Member member = getCurrentMember();
        RepresentativeBadge representativeBadge = representativeBadgeRepository.findByMember(member);

        return MemberInfoResponse.builder()
                .writerEmail(member.getEmail())
                .badge(representativeBadge.getRepresentativeBadge().name)
                .badgeImg(s3Service.getFullUrl(representativeBadge.getRepresentativeBadge().url))
                .build();
    }

    public void deleteFromS3(String urlToDelete) {
        if(!(urlToDelete.equals("profile.jpg"))) {
            s3Service.delete(urlToDelete);
        }
    }

    public void validatePassword(String getPassword, Member member) {
        if (!(passwordEncoder.matches(getPassword, member.getPassword()))) {
            throw new LogInFailurePassword();
        }
    }
}