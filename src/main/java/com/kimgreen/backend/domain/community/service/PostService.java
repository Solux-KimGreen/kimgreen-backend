package com.kimgreen.backend.domain.community.service;

import com.kimgreen.backend.domain.community.entity.Category;
import com.kimgreen.backend.domain.community.entity.Post;
import com.kimgreen.backend.domain.community.entity.PostImg;
import com.kimgreen.backend.domain.community.repository.PostImgRepository;
import com.kimgreen.backend.domain.community.repository.PostRepository;
import com.kimgreen.backend.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kimgreen.backend.domain.community.dto.WritePostRequestDto;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final S3Service s3Service;
    private final PostRepository postRepository;
    private final PostImgRepository postImgRepository;

    @Transactional
    public void writeCheckPost(WritePostRequestDto writePostRequestDto, MultipartFile multipartFile, Member member) throws IOException {
        // s3에 파일을 업로드 한 뒤 예외가 발생하면 db는 롤백이 되지만,
        // 이미 s3에 저장된 이미지는 삭제되지 않는 문제가 있음.

        // post를 먼저 저장
        Post post = postRepository.save(writePostRequestDto.toCertifyPostEntity(
                                        writePostRequestDto.getCategory(),
                                        writePostRequestDto.getContent(), member));

        //파일을 첨부한 경우
        if(multipartFile != null){
            // s3에 첨부파일을 저장하고, db에도 post_file을 저장
            uploadPostFileList(multipartFile, post);
        }
    }

    @Transactional
    public void writeDailyPost(WritePostRequestDto writePostRequestDto, MultipartFile multipartFile, Member member) throws IOException {
        // s3에 파일을 업로드 한 뒤 예외가 발생하면 db는 롤백이 되지만,
        // 이미 s3에 저장된 이미지는 삭제되지 않는 문제가 있음.

        // post를 먼저 저장
        Post post = postRepository.save(writePostRequestDto.toDailyPostEntity(
                writePostRequestDto.getCategory(),
                writePostRequestDto.getContent(), member));

        //파일을 첨부한 경우
        if(multipartFile != null){
            // s3에 첨부파일을 저장하고, db에도 post_file을 저장
            uploadPostFileList(multipartFile, post);
        }
    }

    @Transactional
    public void uploadPostFileList(MultipartFile multipartFile, Post post) throws IOException {
        // s3에 파일을 업로드 한 뒤 예외가 발생하면 db는 롤백이 되지만,
        // 이미 s3에 저장된 이미지는 삭제되지 않는 문제가 있음.

            PostImg postFile = postImgRepository.save(PostImg.builder()
                    .imgUrl(s3Service.saveFile(multipartFile))
                    .title(multipartFile.getOriginalFilename())
                    .post(post).build());
    }
}
