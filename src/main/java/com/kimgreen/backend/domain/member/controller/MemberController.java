package com.kimgreen.backend.domain.member.controller;

import com.kimgreen.backend.domain.member.dto.Auth.DeleteMemberRequestDto;
import com.kimgreen.backend.domain.member.dto.Auth.SignUpRequestDto;
import com.kimgreen.backend.domain.member.dto.Member.NicknameRequestDto;
import com.kimgreen.backend.domain.member.service.MemberService;
import com.kimgreen.backend.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.kimgreen.backend.response.Message.*;
import static com.kimgreen.backend.response.Response.success;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "Member")
@RestController
@AllArgsConstructor
@RequestMapping(value="/member")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "댓글 알림 변경")
    @ResponseStatus(OK)
    @PatchMapping("/alarm")
    public Response changeAlarm() {
        memberService.changeCommentAlarm();
        return success(CHANGE_ALARM_SUCCESS);
    }

    @Operation(summary = "회원 탈퇴")
    @ResponseStatus(OK)
    @DeleteMapping()
    public Response deleteMember(@RequestBody DeleteMemberRequestDto deleteMemberRequestDto) {
        memberService.deleteMember(deleteMemberRequestDto);
        return success(DELETE_MEMBER_SUCCESS);
    }


    @Operation(summary = "닉네임 변경하기")
    @ResponseStatus(OK)
    @PatchMapping("/nickname")
    public Response changeNickname(@RequestBody NicknameRequestDto nicknameRequestDto) {
        memberService.changeNickname(nicknameRequestDto.getNickname());
        return success(CHANGE_NICKNAME_SUCCESS);
    }
    @Operation(summary = "프로필 사진진 변경기")
    @ResponseStatus(OK)
    @PatchMapping(value="/profile-img",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response changeProfileImg(@RequestPart(value="file") MultipartFile multipartFile) throws IOException {
        memberService.changeProfileImg(multipartFile);
        return success(CHANGE_PROFILE_IMG_SUCCESS);
    }

    @Operation(summary = "설정창 정보 불러오기")
    @ResponseStatus(OK)
    @GetMapping()
    public Response getSettingsInfo() {
        return success(GET_MEMBER_INFO_SETTING_SUCCESS, memberService.getSettingInfo());
    }

    @Operation(summary = "사용자 별명 불러오기 불러오기")
    @ResponseStatus(OK)
    @GetMapping("/profile-name")
    public Response getMemberInfo() {
        return success(GET_MEMBER_INFO, memberService.getMemberInfo());
    }


}
