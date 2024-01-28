package com.kimgreen.backend.domain.community.service;

import com.kimgreen.backend.domain.community.dto.GetCommentDto;
import com.kimgreen.backend.domain.community.dto.PostCommentDto;
import com.kimgreen.backend.domain.community.entity.Comment;
import com.kimgreen.backend.domain.community.entity.Post;
import com.kimgreen.backend.domain.community.repository.CommentRepository;
import com.kimgreen.backend.domain.community.repository.PostRepository;
import com.kimgreen.backend.domain.member.entity.Member;
import com.kimgreen.backend.domain.member.entity.MemberProfileImg;
import com.kimgreen.backend.domain.member.repository.MemberProfileImgRepository;
import com.kimgreen.backend.domain.member.service.MemberService;
import com.kimgreen.backend.domain.profile.entity.RepresentativeBadge;
import com.kimgreen.backend.domain.profile.repository.RepresentativeBadgeRepository;
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
    private final GetCommentDto getCommentDto;
    private final S3Service s3Service;
    private final RepresentativeBadgeRepository representativeBadgeRepository;

    private final MemberProfileImgRepository memberProfileImgRepository;

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

    public List<GetCommentDto> getComment(Long postId){
        List <Comment> allCommentList = commentRepository.findAll(); // 모든 댓글 목록
        Post post = postRepository.findById(postId).orElseThrow(PostNotFound::new); // 내가 보고싶은 포스트

        List <GetCommentDto> commentList = new ArrayList<>(); // 리턴

        for (Comment comment : allCommentList){
            if (comment.getPost() == post){
                MemberProfileImg memberProfileImg = memberProfileImgRepository.findByMember(comment.getMember());
                RepresentativeBadge representativeBadge = representativeBadgeRepository.findByMember(comment.getMember());
                /*
                GetCommentDto.from(comment,
                        s3Service.getFullUrl(memberProfileImg.getImgUrl()),
                        representativeBadge.getRepresentativeBadge().name,
                        comment.getMember().getMemberId().equals(memberService.getCurrentMember().getMemberId()));
                    */
                GetCommentDto getCommentDto1 = GetCommentDto.builder()
                        .commentId(comment.getCommentId())
                        .writerProfileImg(s3Service.getFullUrl(memberProfileImg.getImgUrl())) //프로필이미지
                        .writerNickname(comment.getMember().getNickname())
                        .writerBadge(representativeBadge.getRepresentativeBadge().name)
                        .content(comment.getContent())
                        .isWriter(comment.getMember().getMemberId().equals(memberService.getCurrentMember().getMemberId()))
                        .createAt(comment.getCreatedAt())
                        .build();
                commentList.add(getCommentDto1);
            }
        }
        return commentList;
    }

}