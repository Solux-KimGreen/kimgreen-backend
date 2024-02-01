package com.kimgreen.backend.domain.profile.controller;

import com.kimgreen.backend.domain.profile.dto.Profile.GetProfilePostDto;
import com.kimgreen.backend.domain.profile.service.ProfileService;
import com.kimgreen.backend.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

import static com.kimgreen.backend.response.Message.*;
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
    public Response getProfilePosts(@RequestParam("memberId") Long memberId) {
        return success(PROFILE_POSTS_SUCCESS, profileService.response(memberId));
    }
    @Operation(summary = "프로필 정보 불러오기")
    @ResponseStatus(OK)
    @GetMapping()
    public Response getProfileInfo(@RequestParam("memberId") Long memberId) {
        return success(PROFILE_INFO_SUCCESS, profileService.getProfileInfo(memberId));
    }

    @Operation(summary = "설정창 내가 쓴 댓글 불러오기")
    @ResponseStatus(OK)
    @GetMapping("/setting/comment")
    public Response getMyComment() {
        return success(GET_MY_COMMENT_SUCCESS,profileService.getMyComment());
    }

    @Operation(summary = "설정창 내가 쓴 글 불러오기")
    @ResponseStatus(OK)
    @GetMapping("/setting/post")
    public Response getMyPost() {return success(GET_MY_POST_SUCCESS, profileService.getMyPost());}


}
