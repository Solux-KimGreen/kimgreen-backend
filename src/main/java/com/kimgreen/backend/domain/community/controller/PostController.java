package com.kimgreen.backend.domain.community.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kimgreen.backend.domain.community.dto.WritePostRequestDto;
import com.kimgreen.backend.domain.community.entity.Category;
import com.kimgreen.backend.domain.member.entity.Member;
import com.kimgreen.backend.domain.member.service.MemberService;
import com.kimgreen.backend.domain.community.service.PostService;
import com.kimgreen.backend.domain.profile.repository.BadgeRepository;
import com.kimgreen.backend.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

import static com.kimgreen.backend.response.Message.*;
import static com.kimgreen.backend.response.Response.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Tag(name = "Post")
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/post")

public class PostController {

    private final PostService postService;
    private final MemberService memberService;
    private final BadgeRepository badgeRepository;

    @Operation(summary = "게시글 작성(인증)")
    @ResponseStatus(OK)
    @PostMapping(path="/check", consumes = MULTIPART_FORM_DATA_VALUE)
    public Response writeCheckPost(@RequestPart(name = "jsonData") WritePostRequestDto writePostRequestDto,
                                   @RequestPart(name = "File") MultipartFile multipartFile) throws IOException { //파일 필수 O
        postService.writeCheckPost(writePostRequestDto, multipartFile, memberService.getCurrentMember());
        return success(WRITE_CERTIFY_POST_SUCCESS);
    }

    @Operation(summary = "게시글 작성(일상)")
    @ResponseStatus(OK)
    @PostMapping(path="/daily", consumes = MULTIPART_FORM_DATA_VALUE)
    public Response writeDailyPost(@RequestPart(name = "jsonData") WritePostRequestDto writePostRequestDto,
                              @RequestPart(name = "File", required = false) MultipartFile multipartFile) throws IOException { //파일 필수 X
        postService.writeDailyPost(writePostRequestDto, multipartFile, memberService.getCurrentMember());
        return success(WRITE_DAILY_POST_SUCCESS);
    }

    @Operation(summary = "게시글 상세 보기")
    @ResponseStatus(OK)
    @GetMapping()
    public Response getPostInfo(Long postId){
        return success(GET_POST_SUCCESS, postService.getPostInfo(postId));
    }

    @Operation(summary = "게시글 목록 불러오기")
    @ResponseStatus(OK)
    @GetMapping("/{category}")
    public Response getPostList(Category category, com.kimgreen.backend.domain.community.entity.Tag tag) {
        return success(GET_POST_LIST_SUCCESS, postService.getPostlist(category, tag));
    }

    @JsonIgnore
    @Operation(summary = "게시글 좋아요 상위목록 불러오기")
    @ResponseStatus(OK)
    @GetMapping("/best")
    public Response getBestPostList() {
        return Response.success(GET_BEST_POST_LIST_SUCCESS, postService.getBestPostList());
    }

    @Operation(summary = "게시글 삭제하기")
    @ResponseStatus(OK)
    @DeleteMapping()
    public Response deletePost(@RequestParam("postId") Long postId){
        postService.deletePost(postId);
        return success(DELETE_POST_SUCCESS);
    }

    @Operation(summary = "게시글 수정하기")
    @ResponseStatus(OK)
    @PutMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public Response editPostInfo(Long postId,
                                 @RequestPart(name = "body(json)") WritePostRequestDto editPostInfoRequestDto,
                                 @RequestPart(name = "files", required = false) MultipartFile multipartFile) throws IOException {

        postService.editPost(postId, editPostInfoRequestDto, multipartFile);
        return success(EDIT_POST_SUCCESS);
    }
}