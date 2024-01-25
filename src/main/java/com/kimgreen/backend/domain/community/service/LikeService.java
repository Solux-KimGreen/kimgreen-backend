package com.kimgreen.backend.domain.community.service;

import com.kimgreen.backend.domain.community.entity.Likes;
import com.kimgreen.backend.domain.community.entity.Post;
import com.kimgreen.backend.domain.community.repository.LikeRepository;
import com.kimgreen.backend.domain.community.repository.PostRepository;
import com.kimgreen.backend.domain.member.entity.Member;
import com.kimgreen.backend.domain.member.service.MemberService;
import com.kimgreen.backend.exception.PostNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LikeService {

    private final MemberService memberService;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public void setLike(Long postId) {
        Member member = memberService.getCurrentMember();
        Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFound());

        Optional<Likes> foundLike = likeRepository.findByPostAndMember(post,member);
        if(foundLike.isEmpty()) {
            postLike(member, post);
        } else {
            likeRepository.delete(foundLike.get());
        }
    }

    public void postLike(Member member, Post post) {
        likeRepository.save(Likes.builder()
                        .post(post)
                        .member(member)
                        .build());
    }
}
