package com.kimgreen.backend.domain.community.controller;

import com.kimgreen.backend.domain.community.dto.PostCommentDto;
import com.kimgreen.backend.domain.community.entity.Comment;
import com.kimgreen.backend.domain.community.service.CommentService;
import com.kimgreen.backend.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kimgreen.backend.response.Message.*;
import static com.kimgreen.backend.response.Response.success;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "Comment")
@RestController
@RequestMapping(value="/comment")
@AllArgsConstructor
public class CommentController {
    private CommentService commentService;
    @Operation(summary = "댓글 작성")
    @ResponseStatus(OK)
    @PostMapping("/write-comment")
    public Response postComment(@RequestParam Long postId, @RequestBody PostCommentDto postCommentDto) {
        commentService.postComment(postId, postCommentDto);
        return success(POST_COMMENT_SUCCESS);
    }

    @Operation(summary = "댓글 삭제")
    @ResponseStatus(OK)
    @PatchMapping("/delete-comment")
    public Response deleteComment(@RequestParam Long commentId){
        commentService.deleteComment(commentId);
        return success(DELETE_COMMENT_SUCCESS);
    }

    @Operation(summary = "댓글 목록 불러오기")
    @ResponseStatus(OK)
    @PatchMapping("/get-comment")
    public Response getComment(@RequestParam Long postId){
        List <Comment> commentList = commentService.getComment(postId);
        return success(GET_COMMENT_SUCCESS, commentList);
    }
}
