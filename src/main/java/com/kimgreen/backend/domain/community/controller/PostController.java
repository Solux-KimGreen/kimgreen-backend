package com.kimgreen.backend.domain.community.controller;

import com.kimgreen.backend.domain.community.dto.WritePostRequestDto;
import com.kimgreen.backend.domain.member.entity.Member;
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
    public Response getPostInfoWithAuthMember(Long postId){
        return success(GET_POST_SUCCESS, postService.getPostInfoWithAuthMember(postId, memberService.getCurrentMember()));
    }

    @Operation(summary = "게시글 삭제하기")
    @ResponseStatus(OK)
    @DeleteMapping()
    public Response deletePost(@RequestParam("postId") Long postId){
        postService.deletePost(postId, memberService.getCurrentMember());
        return success(DELETE_POST_SUCCESS);
    }

    @Operation(summary = "게시글 수정하기")
    @ResponseStatus(OK)
    @PutMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public Response editPostInfo(Long postId,
                                 @RequestPart(name = "body(json)") WritePostRequestDto editPostInfoRequestDto,
                                 @RequestPart(name = "files", required = false) MultipartFile multipartFile) throws IOException {

        postService.editPost(postId, editPostInfoRequestDto, multipartFile, memberService.getCurrentMember());
        return success(EDIT_POST_SUCCESS);
    }

    /*    @GetMapping("/post/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        BoardDto boardDto = boardService.findById(id);
        model.addAttribute("post", boardDto);
        return "board/edit.html";
    }

    @PutMapping("/post/edit/{id}")
    public String update(@PathVariable Long id, BoardUpdateDto requestDto){
        boardService.update(id,requestDto);
        return "redirect:/board/list";
    }*/
}