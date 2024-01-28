package com.kimgreen.backend.domain.profile.controller;

import com.kimgreen.backend.domain.profile.dto.GetProfileDto;
import com.kimgreen.backend.domain.profile.service.ProfileService;
import com.kimgreen.backend.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
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

    @Operation(summary = "프로필 글 목록 불러오기(특정 멤버가 쓴 글 목록 불러오기)")
    @ResponseStatus(OK)
    @GetMapping("/post")
    public Response getProfilePosts(@RequestParam("memberId") Long memberId,
                                    @RequestParam("page") int page,
                                    @RequestParam("size") int size,
                                    @ParameterObject Pageable sort)
    {
        profileService.getProfilePosts();
        return success(PROFILE_INFO_SUCCESS);
    }
    @Operation(summary = "프로필 정보 불러오기")
    @ResponseStatus(OK)
    @GetMapping()
    public Response getProfileInfo(@RequestParam("memberId") Long memberId) {
        return success(PROFILE_INFO_SUCCESS, profileService.getProfileInfo(memberId));
    }

}
