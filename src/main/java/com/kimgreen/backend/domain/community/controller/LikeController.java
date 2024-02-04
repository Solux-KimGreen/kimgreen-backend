package com.kimgreen.backend.domain.community.controller;

import com.kimgreen.backend.domain.community.repository.PostRepository;
import com.kimgreen.backend.domain.community.service.LikeService;
import com.kimgreen.backend.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.kimgreen.backend.response.Message.*;
import static org.springframework.http.HttpStatus.*;

@Tag(name = "Like")
@RestController
@RequestMapping(value="/post/like")
@AllArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "좋아요 누르기 or 취소하기")
    @ResponseStatus(OK)
    @PostMapping()
    public Response setLike(@RequestParam("postId") Long postId) {
        likeService.setLike(postId);
        return Response.success(SET_LIKES_SUCCESS);
    }
}