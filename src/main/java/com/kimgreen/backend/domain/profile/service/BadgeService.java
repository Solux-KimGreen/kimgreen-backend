package com.kimgreen.backend.domain.profile.service;

import com.kimgreen.backend.domain.BadgeList;
import com.kimgreen.backend.domain.community.service.S3Service;
import com.kimgreen.backend.domain.member.entity.Member;
import com.kimgreen.backend.domain.member.service.MemberService;
import com.kimgreen.backend.domain.profile.dto.Badge.CollectedBadgeResponseDto;
import com.kimgreen.backend.domain.profile.dto.Badge.NotCollectedBadgeResponseDto;
import com.kimgreen.backend.domain.profile.dto.Badge.ProfileBadgeRequestDto;
import com.kimgreen.backend.domain.profile.dto.Badge.RepBadgeRequestDto;
import com.kimgreen.backend.domain.profile.entity.Badge;
import com.kimgreen.backend.domain.profile.entity.ProfileBadge;
import com.kimgreen.backend.domain.profile.entity.RepresentativeBadge;
import com.kimgreen.backend.domain.profile.repository.BadgeRepository;
import com.kimgreen.backend.domain.profile.repository.ProfileBadgeRepository;
import com.kimgreen.backend.domain.profile.repository.RepresentativeBadgeRepository;
import com.kimgreen.backend.exception.BadgeNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;
    private final ProfileBadgeRepository profileBadgeRepository;
    private final RepresentativeBadgeRepository representativeBadgeRepository;
    private final MemberService memberService;
    private final S3Service s3Service;

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

    public List<CollectedBadgeResponseDto> getCollectedBadgeInfo() {
        Member member = memberService.getCurrentMember();
        Badge badge = badgeRepository.findByMember(member);
        updateBadeInfos(badge);
        //<badge, 달성여부>를 저장한 map 반환
        Map<BadgeList,Boolean> badgeMap = getMap(badge);
        //반환할 dto list 삽입
        List<CollectedBadgeResponseDto> returnDto = new ArrayList<>();
        for(BadgeList b: badgeMap.keySet()) {
            if(badgeMap.get(b)) {
                returnDto.add(
                        CollectedBadgeResponseDto.builder()
                                .badge(b.name)
                                .badgeImg(s3Service.getFullUrl(b.url))
                                .build()
                );
            }
        }
        return returnDto;
    }

    public List<NotCollectedBadgeResponseDto> getNotCollectedBadgeInfo() {
        Member member = memberService.getCurrentMember();
        Badge badge = badgeRepository.findByMember(member);
        //<badge, 달성여부>를 저장한 map 반환
        Map<BadgeList,Boolean> map = getMap(badge);
        //<badge, count>를 저장한 map 반환
        Map<BadgeList, Integer> countMap = getCountMap(map,badge);
        //반환할 dto list 삽입
        List<NotCollectedBadgeResponseDto> returnDto = new ArrayList<>();
        for(BadgeList b: countMap.keySet()) {
            if(b==BadgeList.ADVENTURER || b==BadgeList.GOLDEN_KIMGREEN) {
                returnDto.add(
                        NotCollectedBadgeResponseDto.builder()
                                .badgeImg(s3Service.getFullUrl(b.url))
                                .content(b.content)
                                .goal(-1)
                                .build());
            } else {
                returnDto.add(
                        NotCollectedBadgeResponseDto.builder()
                                .badgeImg(s3Service.getFullUrl(b.url))
                                .content(b.content)
                                .completeCount(countMap.get(b))
                                .goal(b.goal)
                                .build());
            }
        }
        return returnDto;
    }

    @Transactional
    public void updateBadeInfos(Badge badge) {
        if(badge.isEarlybirdIsAchieved()&&badge.isReform10IsAchieved()&&badge.isReusable10IsAchieved()
                && badge.isAdventurerIsAchieved() && badge.isReceipt10IsAchieved() && badge.isPlastic10IsAchieved()
                && badge.isTransport10IsAchieved() && badge.isPlogging10IsAchieved() && badge.isEtc10IsAchieved()
                && badge.isMentorIsAchieved() && badge.isMenteeIsAchieved() && badge.isCertification50IsAchieved()) {
            badge.updateGoldenAchieved();
        } else {
            badge.updateGoldenNotAchieved();
        }

        if(badge.getEtcCount()>=1 && badge.getReformCount()>=1 && badge.getReceiptCount()>=1 && badge.getReusableCount()>=1
            && badge.getPlasticCount()>=1 && badge.getPloggingCount()>=1 && badge.getTransportCount()>=1) {
            badge.updateAdventurerAchieved();
        } else {
            badge.updateAdventurerNotAchieved();
        }
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

    public Map<BadgeList,Boolean> getMap(Badge badge) {
        Map<BadgeList,Boolean> map = new LinkedHashMap<>();

        if(badge.isCertification10IsAchieved()){map.put(BadgeList.NORANG,true);}
        else {map.put(BadgeList.NORANG,false);}
        if(badge.isCertification20IsAchieved()){map.put(BadgeList.YEONDU,true);}
        else {map.put(BadgeList.YEONDU,false);}
        if(badge.isCertification50IsAchieved()){map.put(BadgeList.GREEN,true);}
        else {map.put(BadgeList.GREEN,false);}
        if(badge.isMenteeIsAchieved()){map.put(BadgeList.MENTEE,true);}
        else {map.put(BadgeList.MENTEE,false);}
        if(badge.isMentorIsAchieved()){map.put(BadgeList.MENTOR,true);}
        else {map.put(BadgeList.MENTOR,false);}
        if(badge.isPlastic3IsAchieved()){map.put(BadgeList.PLASTIC_3,true);}
        else {map.put(BadgeList.PLASTIC_3,false);}
        if(badge.isPlastic10IsAchieved()){map.put(BadgeList.PLASTIC_10,true);}
        else {map.put(BadgeList.PLASTIC_10,false);}
        if(badge.isPlogging3IsAchieved()){map.put(BadgeList.PLOGGING_3,true);}
        else {map.put(BadgeList.PLOGGING_3,false);}
        if(badge.isPlogging10IsAchieved()){map.put(BadgeList.PLOGGING_10,true);}
        else {map.put(BadgeList.PLOGGING_10,false);}
        if(badge.isReceipt3IsAchieved()){map.put(BadgeList.RECEIPT_3,true);}
        else {map.put(BadgeList.RECEIPT_3,false);}
        if(badge.isReceipt10IsAchieved()){map.put(BadgeList.RECEIPT_10,true);}
        else {map.put(BadgeList.RECEIPT_10,false);}
        if(badge.isReform3IsAchieved()){map.put(BadgeList.REFORM_3,true);}
        else {map.put(BadgeList.REFORM_3,false);}
        if(badge.isReform10IsAchieved()){map.put(BadgeList.REFORM_10,true);}
        else {map.put(BadgeList.REFORM_10,false);}
        if(badge.isReusable3IsAchieved()){map.put(BadgeList.REUSABLE_3,true);}
        else {map.put(BadgeList.REUSABLE_3,false);}
        if(badge.isReusable10IsAchieved()){map.put(BadgeList.REFORM_10,true);}
        else {map.put(BadgeList.REFORM_10,false);}
        if(badge.isTransport3IsAchieved()){map.put(BadgeList.TRANSPORT_3,true);}
        else {map.put(BadgeList.TRANSPORT_3,false);}
        if(badge.isTransport10IsAchieved()){map.put(BadgeList.TRANSPORT_10,true);}
        else {map.put(BadgeList.TRANSPORT_10,false);}
        if(badge.isEtc3IsAchieved()){map.put(BadgeList.ETC_3,true);}
        else {map.put(BadgeList.ETC_3,false);}
        if(badge.isEtc10IsAchieved()){map.put(BadgeList.ETC_10,true);}
        else {map.put(BadgeList.ETC_10,false);}
        if(badge.isEarlybirdIsAchieved()){map.put(BadgeList.EARLYBIRD,true);}
        else {map.put(BadgeList.EARLYBIRD,false);}
        if(badge.isAdventurerIsAchieved()) {map.put(BadgeList.ADVENTURER,true);}
        else {map.put(BadgeList.ADVENTURER,false);}
        if(badge.isGoldenIsAchieved()){map.put(BadgeList.GOLDEN_KIMGREEN,true);}
        else {map.put(BadgeList.GOLDEN_KIMGREEN,false);}
        return  map;

    }

    public Map<BadgeList, Integer> getCountMap(Map<BadgeList,Boolean> badgeMap,Badge badge) {
        Map<BadgeList,Integer> map = new LinkedHashMap<>();
        for(BadgeList badges: badgeMap.keySet()) {
            if(badgeMap.get(badges)==false) {
                if(badges.equals(BadgeList.NORANG)) {map.put(BadgeList.NORANG,badge.getCertificationCount());}
                if(badges.equals(BadgeList.YEONDU)) {map.put(BadgeList.YEONDU,badge.getCertificationCount());}
                if(badges.equals(BadgeList.GREEN)) {map.put(BadgeList.GREEN,badge.getCertificationCount());}
                if(badges.equals(BadgeList.MENTEE)) {map.put(BadgeList.MENTEE,badge.getMenteeCount());}
                if(badges.equals(BadgeList.MENTOR)) {map.put(BadgeList.MENTOR,badge.getMentorCount());}
                if(badges.equals(BadgeList.PLASTIC_3)) {map.put(BadgeList.PLASTIC_3,badge.getPlasticCount());}
                if(badges.equals(BadgeList.PLASTIC_10)) {map.put(BadgeList.PLASTIC_10,badge.getPlasticCount());}
                if(badges.equals(BadgeList.PLOGGING_3)) {map.put(BadgeList.PLOGGING_3,badge.getPloggingCount());}
                if(badges.equals(BadgeList.PLOGGING_10)) {map.put(BadgeList.PLOGGING_10,badge.getPloggingCount());}
                if(badges.equals(BadgeList.RECEIPT_3)) {map.put(BadgeList.RECEIPT_3,badge.getReceiptCount());}
                if(badges.equals(BadgeList.RECEIPT_10)) {map.put(BadgeList.RECEIPT_10,badge.getReceiptCount());}
                if(badges.equals(BadgeList.REFORM_3)) {map.put(BadgeList.REFORM_3,badge.getReformCount());}
                if(badges.equals(BadgeList.REFORM_10)) {map.put(BadgeList.REFORM_10,badge.getReformCount());}
                if(badges.equals(BadgeList.REUSABLE_3)) {map.put(BadgeList.REUSABLE_3,badge.getReusableCount());}
                if(badges.equals(BadgeList.REUSABLE_10)) {map.put(BadgeList.REUSABLE_10,badge.getReusableCount());}
                if(badges.equals(BadgeList.TRANSPORT_3)) {map.put(BadgeList.TRANSPORT_3,badge.getTransportCount());}
                if(badges.equals(BadgeList.TRANSPORT_10)) {map.put(BadgeList.TRANSPORT_10,badge.getTransportCount());}
                if(badges.equals(BadgeList.ETC_3)) {map.put(BadgeList.ETC_3,badge.getEtcCount());}
                if(badges.equals(BadgeList.ETC_10)) {map.put(BadgeList.ETC_10,badge.getEtcCount());}
                if(badges.equals(BadgeList.EARLYBIRD)) {map.put(BadgeList.EARLYBIRD,badge.getEarlybirdCount());}
                if(badges.equals(BadgeList.ADVENTURER)) {map.put(BadgeList.ADVENTURER,-1);}
                if(badges.equals(BadgeList.GOLDEN_KIMGREEN)) {map.put(BadgeList.GOLDEN_KIMGREEN,-1);}

            }
        }
        return map;
    }
}
