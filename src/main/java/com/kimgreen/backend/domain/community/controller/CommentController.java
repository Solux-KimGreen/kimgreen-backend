package com.kimgreen.backend.domain.community.controller;

import com.kimgreen.backend.domain.community.dto.PostCommentDto;
import com.kimgreen.backend.domain.community.service.CommentService;
import com.kimgreen.backend.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    @PatchMapping("/write-comment")
    public Response changeAlarm(@RequestParam Long postId, @RequestBody PostCommentDto postCommentDto) {
        commentService.postComment(postId, postCommentDto);
        return success(POST_COMMENT_SUCCESS);
    }
}
