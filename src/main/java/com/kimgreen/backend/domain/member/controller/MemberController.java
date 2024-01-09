package com.kimgreen.backend.domain.member.controller;

import com.kimgreen.backend.domain.member.dto.Auth.SignUpRequestDto;
import com.kimgreen.backend.domain.member.service.MemberService;
import com.kimgreen.backend.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public Response changeAlarm(@RequestParam String type) {
        memberService.changeAlarm(type);
        return success(CHANGE_ALARM_SUCCESS);
    }

    @Operation(summary = "회원 탈퇴")
    @ResponseStatus(OK)
    @DeleteMapping()
    public Response deleteMember() {
        memberService.deleteMember();
        return success(DELETE_MEMBER_SUCCESS);
    }

}
