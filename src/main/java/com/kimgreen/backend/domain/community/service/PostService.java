package com.kimgreen.backend.domain.community.service;

import com.kimgreen.backend.domain.community.dto.GetPostInfoResponseDto;
import com.kimgreen.backend.domain.community.dto.WritePostRequestDto;
import com.kimgreen.backend.domain.community.entity.*;
import com.kimgreen.backend.domain.community.repository.PostImgRepository;
import com.kimgreen.backend.domain.community.repository.PostRepository;
import com.kimgreen.backend.domain.member.entity.Member;
import com.kimgreen.backend.domain.profile.entity.RepresentativeBadge;
import com.kimgreen.backend.domain.profile.repository.RepresentativeBadgeRepository;
import com.kimgreen.backend.exception.PostNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final S3Service s3Service;
    private final PostRepository postRepository;
    private final PostImgRepository postImgRepository;
    private final RepresentativeBadgeRepository representativeBadgeRepository;

    //게시물 작성
    @Transactional
    public void writeCheckPost(WritePostRequestDto writePostRequestDto, MultipartFile multipartFile, Member member) throws IOException {

        Post post = postRepository.save(writePostRequestDto.toCertifyPostEntity(
                                        writePostRequestDto.getCategory(),
                                        writePostRequestDto.getContent(), member));

        if(multipartFile != null){
            uploadPostFileList(multipartFile, post);
        }
    }

    @Transactional
    public void writeDailyPost(WritePostRequestDto writePostRequestDto, MultipartFile multipartFile, Member member) throws IOException {

        Post post = postRepository.save(writePostRequestDto.toDailyPostEntity(
                writePostRequestDto.getCategory(),
                writePostRequestDto.getContent(), member));

        if(multipartFile != null){
            uploadPostFileList(multipartFile, post);
        }
    }

    // 게시글 상세정보 조회
    @Transactional
    public GetPostInfoResponseDto getPostInfoWithAuthMember(Long postId, Member currentMember) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFound::new);
        Member member = post.getMember();
        RepresentativeBadge representativeBadge = representativeBadgeRepository.findByMember(member);

        return GetPostInfoResponseDto.from(post,
                isWriter(post, currentMember),
                isLiked(post, currentMember),
                representativeBadge.getRepresentativeBadge().name);
    }

    @Transactional(readOnly = true)
    public Boolean isWriter(Post post, Member currentMember){
        return post.getMember().getMemberId().equals(currentMember.getMemberId());
    }

    @Transactional(readOnly = true)
    public Boolean isLiked(Post post, Member currentMember){
        for(Likes postLike : post.getLikes()){
            if(postLike.getLikeId().equals(currentMember.getMemberId())) return true;
        }
        return false;
    }

    @Transactional
    public void uploadPostFileList(MultipartFile multipartFile, Post post) throws IOException {
            PostImg postFile = postImgRepository.save(PostImg.builder()
                    .imgUrl(s3Service.saveFile(multipartFile))
                    .title(multipartFile.getOriginalFilename())
                    .post(post).build());
    }
}
