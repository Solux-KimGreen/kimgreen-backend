package com.kimgreen.backend.domain.community.service;

import com.kimgreen.backend.domain.community.repository.PostRepository;
import com.kimgreen.backend.domain.member.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {
    private final MemberService memberService;
    private final PostRepository postRepository;


}
