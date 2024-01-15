package com.kimgreen.backend.domain.community.service;

import com.kimgreen.backend.domain.community.dto.PostCommentDto;
import com.kimgreen.backend.domain.community.entity.Comment;
import com.kimgreen.backend.domain.community.entity.Post;
import com.kimgreen.backend.domain.community.repository.CommentRepository;
import com.kimgreen.backend.domain.community.repository.PostRepository;
import com.kimgreen.backend.domain.member.entity.Member;
import com.kimgreen.backend.domain.member.service.MemberService;
import com.kimgreen.backend.exception.PostNotFound;
import com.kimgreen.backend.exception.WrongPath;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor

public class CommentService {

    private final MemberService memberService;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    public void postComment(Long postId, PostCommentDto postCommentDto){
        Member member = memberService.getCurrentMember();
        Post post = postRepository.findById(postId).orElseThrow(PostNotFound::new);

        Comment comment = Comment.builder()
                .content(postCommentDto.getContent())
                .post(post)
                .member(member)
                .build();
        // 데이터 베이스에 저장하기
        commentRepository.save(comment);
        // 명세서의 오류들 처리하기???
    }

    public void deleteComment(Long commentId){
        Member member = memberService.getCurrentMember();
        Comment comment = commentRepository.findById(commentId).orElseThrow(WrongPath::new);
        commentRepository.delete(comment);

    }

    public List<Comment> getComment(Long postId){
        List <Comment> allCommentList = commentRepository.findAll();
        Post post = postRepository.findById(postId).orElseThrow(PostNotFound::new);

        List <Comment> commentList = null;
        for (Comment comment : allCommentList){
            if (comment.getPost() == post){
                commentList.add(comment);
            }
        }
        return commentList;
    }
}
