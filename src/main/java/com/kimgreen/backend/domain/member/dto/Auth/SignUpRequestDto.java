package com.kimgreen.backend.domain.member.dto.Auth;

import com.kimgreen.backend.domain.member.entity.Member;
import com.kimgreen.backend.domain.member.entity.MemberProfileImg;
import com.kimgreen.backend.domain.member.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
    private String email;
    private String password;
    private String nickname;

    public Member toMemberEntity(String email, String password, String nickname) {
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .commentAlarm(false).likeAlarm(false)
                .role(Role.ROLE_USER)
                .build();
    }

    public MemberProfileImg toMemberProfileImgEntity(Member member) {
        return MemberProfileImg.builder()
                .member(member)
                .imgUrl("profile.jpg")
                .title("basic_profile")
                .build();
    }
}
