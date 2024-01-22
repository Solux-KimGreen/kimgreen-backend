package com.kimgreen.backend.domain.profile.controller;

import com.kimgreen.backend.domain.profile.service.ProfileService;
import com.kimgreen.backend.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.kimgreen.backend.response.Message.PROFILE_INFO_SUCCESS;
import static com.kimgreen.backend.response.Response.success;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "Profile")
@RestController
@RequestMapping(value="/profile")
@RequiredArgsConstructor
public class profileController {
    private final ProfileService profileService;

    @Operation(summary = "프로필 정보 불러오기")
    @ResponseStatus(OK)
    @GetMapping("/profile-info")
    public Response getProfileInfo(@RequestParam("memberId") Long memberId) {
        List<Object> profileInfo = profileService.getProfileInfo(memberId);
        return success(PROFILE_INFO_SUCCESS, profileInfo);
    }
}
