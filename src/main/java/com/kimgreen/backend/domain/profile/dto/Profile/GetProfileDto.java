package com.kimgreen.backend.domain.profile.dto.Profile;

import com.kimgreen.backend.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class GetProfileDto {
    private String nickname;
    private String profileImg;
    private String profileBadge;
    private String profileBadgeImg;
    private ArrayList<String> badgeList;
    private ArrayList<String> badgeImgList;
    private boolean isMine;

    public GetProfileDto from(Member member,
                              String profileImg,
                              String profileBadge,
                              String profileBadgeImg,
                              ArrayList<String> badgeList,
                              ArrayList<String> badgeImgList,
                              boolean isMine){
        return GetProfileDto.builder()
                .nickname(member.getNickname())
                .profileImg(profileImg)
                .profileBadge(profileBadge)
                .profileBadgeImg(profileBadgeImg)
                .badgeList(badgeList)
                .badgeImgList(badgeImgList)
                .isMine(isMine)
                .build();
    }
}