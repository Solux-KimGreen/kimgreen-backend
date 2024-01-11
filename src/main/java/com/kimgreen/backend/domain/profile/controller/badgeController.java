package com.kimgreen.backend.domain.profile.controller;

import com.kimgreen.backend.domain.member.dto.Auth.SignUpRequestDto;
import com.kimgreen.backend.domain.member.repository.MemberRepository;
import com.kimgreen.backend.domain.profile.dto.RepBadgeRequestDto;
import com.kimgreen.backend.domain.profile.repository.BadgeRepository;
import com.kimgreen.backend.domain.profile.service.BadgeService;
import com.kimgreen.backend.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.kimgreen.backend.response.Message.SIGN_UP_SUCCESS;
import static com.kimgreen.backend.response.Response.success;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "badge")
@RestController
@AllArgsConstructor
@RequestMapping(value="/badge")
public class badgeController {

    private final BadgeService badgeService;

    @Operation(summary = "대표뱃지 설정하기")
    @ResponseStatus(OK)
    @PatchMapping()
    public Response changeRepBadge(@RequestBody RepBadgeRequestDto repBadgeRequestDto) {
        badgeService.changeRepBadge(repBadgeRequestDto);
        return success(SIGN_UP_SUCCESS);
    }
}
