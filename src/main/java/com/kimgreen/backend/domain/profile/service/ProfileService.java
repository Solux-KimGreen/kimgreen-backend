package com.kimgreen.backend.domain.profile.service;

import com.kimgreen.backend.domain.BadgeList;
import com.kimgreen.backend.domain.community.entity.Comment;
import com.kimgreen.backend.domain.community.entity.Likes;
import com.kimgreen.backend.domain.community.entity.Post;
import com.kimgreen.backend.domain.community.entity.PostImg;
import com.kimgreen.backend.domain.community.repository.CommentRepository;
import com.kimgreen.backend.domain.community.repository.LikeRepository;
import com.kimgreen.backend.domain.community.repository.PostImgRepository;
import com.kimgreen.backend.domain.community.repository.PostRepository;
import com.kimgreen.backend.domain.community.service.S3Service;
import com.kimgreen.backend.domain.member.entity.Member;
import com.kimgreen.backend.domain.member.entity.MemberProfileImg;
import com.kimgreen.backend.domain.member.repository.MemberProfileImgRepository;
import com.kimgreen.backend.domain.member.repository.MemberRepository;
import com.kimgreen.backend.domain.member.service.MemberService;
import com.kimgreen.backend.domain.profile.dto.Profile.CommentResponseDto;
import com.kimgreen.backend.domain.profile.dto.Profile.GetProfileDto;
import com.kimgreen.backend.domain.profile.dto.Profile.GetSettingPostDto;
import com.kimgreen.backend.domain.profile.entity.ProfileBadge;
import com.kimgreen.backend.domain.profile.entity.RepresentativeBadge;
import com.kimgreen.backend.domain.profile.repository.ProfileBadgeRepository;
import com.kimgreen.backend.domain.profile.repository.RepresentativeBadgeRepository;
import com.kimgreen.backend.exception.PostNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.kimgreen.backend.domain.profile.dto.Profile.GetProfilePostDto;

import java.util.*;

@Service
@AllArgsConstructor


public class ProfileService {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final GetProfileDto getProfileDto;
    private final MemberProfileImgRepository memberProfileImgRepository;
    private final S3Service s3Service;
    private final RepresentativeBadgeRepository representativeBadgeRepository;
    private final ProfileBadgeRepository profileBadgeRepository;
    private final PostRepository postRepository;
    private final PostImgRepository postImgRepository;
    private final GetProfilePostDto getProfilePostDto;
    private final CommentRepository commentRepository;
    private  final LikeRepository likeRepository;

    public List<GetProfilePostDto> response(Long memberId){
        List<GetProfilePostDto> list = new ArrayList<>();
        List<Post> allPostList = postRepository.findAll();
        List<Post> postList = new ArrayList<>();
        for(Post p : allPostList){
            if(p.getMember().getMemberId().equals(memberId)){
                postList.add(p);
            }
        }
        for(Post p : postList) {
            boolean isLiked = false;
            for(Likes l : p.getLikes()){
                if(l.getMember().getMemberId().equals(memberService.getCurrentMember().getMemberId())){
                    isLiked = true;
                    break;
                }
            }
            if(postImgRepository.findByPost(p) == null){
                list.add(GetProfilePostDto.builder()
                        .postId(p.getPostId())
                        .writerNickname(p.getMember().getNickname())
                        .writerBadge(representativeBadgeRepository.findByMember(p.getMember()).getRepresentativeBadge().name)
                        .writerProfileImg(s3Service.getFullUrl(memberProfileImgRepository.findByMember(p.getMember()).getImgUrl()))
                        .content(p.getContent())
                        .likeCount(p.getLikes().size())
                        .commentCount(p.getComments().size())
                        .isLiked(isLiked).build());
            }else{ // 포스트이미지 있을때
                list.add(GetProfilePostDto.builder()
                        .postId(p.getPostId())
                        .writerNickname(p.getMember().getNickname())
                        .writerBadge(representativeBadgeRepository.findByMember(p.getMember()).getRepresentativeBadge().name)
                        .writerProfileImg(s3Service.getFullUrl(memberProfileImgRepository.findByMember(p.getMember()).getImgUrl()))
                        .content(p.getContent())
                        .likeCount(p.getLikes().size())
                        .commentCount(p.getComments().size())
                        .imgUrl(s3Service.getFullUrl(postImgRepository.findByPost(p).getImgUrl()))
                        .isLiked(isLiked).build());
            }

        }
        return list;
    }

    public GetProfileDto getProfileInfo(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(); // 찾고싶은 멤버
        MemberProfileImg memberProfileImg = memberProfileImgRepository.findByMember(member);
        RepresentativeBadge representativeBadge = representativeBadgeRepository.findByMember(member);
        profileBadgeRepository.findByMember(member);

        ArrayList<String> badgeList = new ArrayList<>();
        ArrayList<String> badgeImgList = new ArrayList<>();

        ProfileBadge profileBadge = profileBadgeRepository.findByMember(member);
        ArrayList<BadgeList> list = new ArrayList<>();
        list.add(profileBadge.getProfileBadge_1());
        list.add(profileBadge.getProfileBadge_2());
        list.add(profileBadge.getProfileBadge_3());
        list.add(profileBadge.getProfileBadge_4());
        list.add(profileBadge.getProfileBadge_5());
        for(BadgeList b : list){
            badgeList.add(b.name);
            if(b != BadgeList.BLANK){
                badgeImgList.add(s3Service.getFullUrl(b.url));
            }
        }

        return getProfileDto.from(member,
                s3Service.getFullUrl(memberProfileImg.getImgUrl()),
                representativeBadge.getRepresentativeBadge().name,
                s3Service.getFullUrl(representativeBadge.getRepresentativeBadge().url),
                badgeList,
                badgeImgList,
                memberId.equals(memberService.getCurrentMember().getMemberId())
                );
    }

    public List<CommentResponseDto> getMyComment() {
        Member member = memberService.getCurrentMember();
        String writer = member.getNickname();
        String writerBadge = representativeBadgeRepository.findByMember(member).getRepresentativeBadge().name;
        List<Comment> comments = commentRepository.findByMember(member);

        List<CommentResponseDto> dto = new ArrayList<>();
        for(Comment comment : comments) {
            Post post = postRepository.findById(comment.getPost().getPostId()).orElseThrow(PostNotFound::new);
            CommentResponseDto commentDto =  CommentResponseDto.toDto(comment.getCommentId(),post.getPostId(),writerBadge,writer,comment.getContent());
            dto.add(commentDto);
        }
        return dto;
    }

    public List<GetSettingPostDto> getMyPost() {
        Member member = memberService.getCurrentMember();
        String writer = member.getNickname();
        String writerBadge = representativeBadgeRepository.findByMember(member).getRepresentativeBadge().name;
        String writerProfileImg = s3Service.getFullUrl(memberProfileImgRepository.findByMember(member).getImgUrl());
        List<Post> posts = postRepository.findByMember(member);

        List<GetSettingPostDto> dto = new ArrayList<>();
        for (Post post : posts) {
            Long countLike = likeRepository.countLike(post.getPostId());
            Long countComment = commentRepository.countComment(post.getPostId());
            PostImg postImg = postImgRepository.findByPost(post);
            //Long postId, String content, String writerBadge, String writerNickname, String writerProfileImg, int likeCount, int commentCount, String imgUrl
            if (postImg != null) {
                dto.add(GetSettingPostDto.toDto(post.getPostId(), post.getContent(), writerBadge, writer, writerProfileImg, countLike, countComment, s3Service.getFullUrl(postImg.getImgUrl())));
            } else {
                dto.add(GetSettingPostDto.toDto(post.getPostId(), post.getContent(), writerBadge, writer, writerProfileImg, countLike, countComment));
            }

        }
        return dto;
    }

}
