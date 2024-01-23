package com.kimgreen.backend.domain.community.controller;

import com.kimgreen.backend.domain.community.dto.WritePostRequestDto;
import com.kimgreen.backend.domain.member.service.MemberService;
import com.kimgreen.backend.domain.community.service.PostService;
import com.kimgreen.backend.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    @Operation(summary = "게시글 작성(인증)")
    @ResponseStatus(OK)
    @PostMapping(path="/check", consumes = MULTIPART_FORM_DATA_VALUE)
    public Response writeCheckPost(@RequestPart(name = "jsonData") @Valid WritePostRequestDto writePostRequestDto,
                                   @RequestPart(name = "Files") MultipartFile multipartFiles) throws IOException { //파일 필수 O
        postService.writeCheckPost(writePostRequestDto, multipartFiles, memberService.getCurrentMember());
        return success(SUCCESS_TO_WRITE_CERTIFY_POST);
    }

    @Operation(summary = "게시글 작성(일상)")
    @ResponseStatus(OK)
    @PostMapping(path="/daily", consumes = MULTIPART_FORM_DATA_VALUE)
    public Response writeDailyPost(@RequestPart(name = "jsonData") WritePostRequestDto writePostRequestDto,
                              @RequestPart(name = "Files", required = false) MultipartFile multipartFiles) throws IOException { //파일 필수 X
        postService.writeDailyPost(writePostRequestDto, multipartFiles, memberService.getCurrentMember());
        return success(SUCCESS_TO_WRITE_DAILY_POST);
    }

    @Operation(summary = "게시글 상세 보기")
    @ResponseStatus(OK)
    @GetMapping("/{postId}")
    public Response getPostInfoWithAuthMember(Long postId){
        return success(SUCCESS_TO_GET_POST, postService.getPostInfoWithAuthMember(postId, memberService.getCurrentMember()));
    }

/*    @Operation(summary = "Delete post API", description = "put post id what you want to delete.")
    @ResponseStatus(OK)
    @DeleteMapping()
    public Response deletePost(Long postId){
        postService.deletePost(postId, memberService.getCurrentMember());
        return Response.success(SUCCESS_TO_DELETE_POST);
    }
    @Operation(summary = "Edit post info API", description = "put post info what you want to edit.")
    @ResponseStatus(OK)
    @PutMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public Response editPostInfo(Long postId,
                                 @Valid @RequestPart(name = "body(json)") WritePostRequestDto editPostInfoRequestDto,
                                 @RequestPart(name = "files", required = false) List<MultipartFile> multipartFiles){
        postService.editPostInfo(postId, editPostInfoRequestDto, multipartFiles, memberService.getCurrentMember());
        return Response.success(SUCCESS_TO_EDIT_POST);
    }*/


}