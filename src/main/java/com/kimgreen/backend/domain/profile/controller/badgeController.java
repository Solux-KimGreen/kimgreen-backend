package com.kimgreen.backend.domain.profile.controller;

import com.kimgreen.backend.domain.profile.dto.Badge.ProfileBadgeRequestDto;
import com.kimgreen.backend.domain.profile.dto.Badge.RepBadgeRequestDto;
import com.kimgreen.backend.domain.profile.service.BadgeService;
import com.kimgreen.backend.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.kimgreen.backend.response.Message.*;
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
        return success(CHANGE_REPRESENTATIVE_BADGE_SUCCESS);
    }

    @Operation(summary = "프로필뱃지 설정하기")
    @ResponseStatus(OK)
    @PatchMapping("/profile")
    public Response changeProfileBadges(@RequestBody ProfileBadgeRequestDto profileBadgeRequestDto) {
        badgeService.changeProfileBadge(profileBadgeRequestDto);
        return success(CHANGE_PROFILE_BADGE_SUCCESS);
    }

    @Operation(summary = "획득뱃지 상세정보 불러오기")
    @ResponseStatus(OK)
    @GetMapping("/collect")
    public Response getCollectedBadgeInfo() {
        return success(GET_COLLECTED_BADGE_INFO,badgeService.getCollectedBadgeInfo());
    }
}
