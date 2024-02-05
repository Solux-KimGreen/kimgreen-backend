package com.kimgreen.backend.domain.profile.service;

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
import com.kimgreen.backend.domain.profile.dto.Calendar.CalendarDetailDto;
import com.kimgreen.backend.domain.profile.dto.Calendar.CalendarDetailRequestDto;
import com.kimgreen.backend.domain.profile.dto.Calendar.CalendarDetailResponseDto;
import com.kimgreen.backend.domain.profile.dto.Calendar.CalendarResponseDto;
import com.kimgreen.backend.domain.profile.entity.RepresentativeBadge;
import com.kimgreen.backend.domain.profile.repository.RepresentativeBadgeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@AllArgsConstructor
public class CalendarService {
    private final PostRepository postRepository;
    private final MemberService memberService;
    private final RepresentativeBadgeRepository representativeBadgeRepository;
    private final PostImgRepository postImgRepository;
    private final MemberProfileImgRepository memberProfileImgRepository;
    private final S3Service s3Service;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    public List<CalendarResponseDto> getCalendar(Long memberId, String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime localDateTime = LocalDate.parse(date, formatter).withDayOfMonth(1).atStartOfDay();
        LocalDateTime start = localDateTime;
        LocalDateTime end = localDateTime.withDayOfMonth(start.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59);

        List<Post> postList = postRepository.findAllBetweenDate(memberId,start,end);
        return getDtoList(postList);
    }

    public CalendarDetailResponseDto getCalendarDetails(CalendarDetailRequestDto dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime start = LocalDate.parse(dto.getDate(), formatter).atStartOfDay();
        LocalDateTime end = start.withHour(23).withMinute(59).withSecond(59);
        List<Post> postList = postRepository.findAllByCreatedAtBetween(start,end);
        return CalendarDetailResponseDto.toDto(postList.size(),getResult(postList));

    }

    public List<CalendarResponseDto> getDtoList(List<Post> postList) {
        Map<String,Integer> resultMap = new HashMap<>();
        for(Post post:postList) {
            String key = post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if(!resultMap.containsKey(key)) {
                resultMap.put(key,1);
            } else {
                Integer value =resultMap.get(key);
                resultMap.put(key,++value);
            }
        }

        return toList(resultMap);
    }

    public List<CalendarResponseDto> toList(Map<String, Integer> maps) {
        List<CalendarResponseDto> result = new ArrayList<>();
        for(Map.Entry<String, Integer> map  :maps.entrySet()) {
            result.add(CalendarResponseDto.builder()
                    .date(map.getKey())
                    .postCount(map.getValue())
                    .build());
        }
        return result;
    }

    public List<CalendarDetailDto> getResult(List<Post> postList) {
        CalendarDetailDto dto;
        List<CalendarDetailDto> result = new ArrayList<>();
        for(Post post : postList) {
            Long postId = post.getPostId();
            Member member = post.getMember();
            RepresentativeBadge badge = representativeBadgeRepository.findByMember(member);
            MemberProfileImg profileImg = memberProfileImgRepository.findByMember(member);
            PostImg postImg = postImgRepository.findByPost(post);
            List<Likes> likeList = post.getLikes();
            boolean isLiked = isLiked(likeList,member);
            //Long likes = likeRepository.countLike(postId);
            Long comments = commentRepository.countComment(postId);


            if(postImg==null) {
                dto = dtoBuilderWithNoImg(postId,member.getNickname(),s3Service.getFullUrl(profileImg.getImgUrl()),badge.getRepresentativeBadge().name
                        ,post.getContent(),likeList.size(),comments,isLiked);
            } else {
                dto = dtoBuilderWithImg(postId, member.getNickname(), s3Service.getFullUrl(profileImg.getImgUrl()), badge.getRepresentativeBadge().name
                        , post.getContent(), likeList.size(), comments, s3Service.getFullUrl(postImg.getImgUrl()),isLiked);
            }

            result.add(dto);
        }
        return result;
    }

    public CalendarDetailDto dtoBuilderWithImg(Long postId, String nickname, String profileImg,String name,String content
            ,int likes, Long comments, String postImg, boolean isLiked) {
        return CalendarDetailDto.builder()
                .postId(postId)
                .writerNickname(nickname)
                .profileImg(profileImg)
                .writerBadge(name)
                .content(content)
                .likeCount(likes)
                .commentCount(comments)
                .imgUrl(postImg)
                .isLiked(isLiked)
                .build();
    }

    public CalendarDetailDto dtoBuilderWithNoImg(Long postId, String nickname, String profileImg,String name,String content
            ,int likes, Long comments, boolean isLiked) {
        return CalendarDetailDto.builder()
                .postId(postId)
                .writerNickname(nickname)
                .profileImg(profileImg)
                .writerBadge(name)
                .content(content)
                .likeCount(likes)
                .commentCount(comments)
                .isLiked(isLiked)
                .build();
    }

    public boolean isLiked(List<Likes> likesList,Member member) {
        for(Likes like : likesList) {
            if(like.getLikeId().equals(member.getMemberId())) {
            if(like.getMember().getMemberId().equals(member.getMemberId())) {
                return true;
            }
        }
        return false;
    }
