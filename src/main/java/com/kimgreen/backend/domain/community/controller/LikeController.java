package com.kimgreen.backend.domain.community.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Like")
@RestController
@RequestMapping(value="/post/like")
public class LikeController {
}
