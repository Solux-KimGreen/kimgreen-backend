package com.kimgreen.backend.domain.community.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.kimgreen.backend.domain.community.entity.Post;
import com.kimgreen.backend.domain.community.entity.PostImg;
import com.kimgreen.backend.domain.community.repository.PostImgRepository;
import com.kimgreen.backend.domain.member.entity.MemberProfileImg;
import com.kimgreen.backend.domain.member.repository.MemberProfileImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final PostImgRepository postImgRepository;
    private final MemberProfileImgRepository memberProfileImgRepository;
    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    //post에 첨부되는 이미지 업로드
    public String saveFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String createdFilename = createFileName(originalFilename);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, createdFilename, multipartFile.getInputStream(), metadata);

        uploadDB(PostImg.builder()
                .imgUrl(createdFilename)
                .title(originalFilename)
                .build());
        return createdFilename;
    }

    //프로필 이미지 업로드
    public String saveProfileFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String createdFilename = createFileName(originalFilename);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, createdFilename, multipartFile.getInputStream(), metadata);

        return createdFilename;
    }

    //삭제
    public void delete(String url) {
        amazonS3.deleteObject(bucket, url);
    }
    //db에 저장 - 필요없으면 삭제 O
    public void uploadDB(PostImg postImg) {postImgRepository.save(postImg);}

    //랜덤이름 생성
    public String createFileName(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        return UUID.randomUUID().toString()+fileExtension;
    }

    //실제 주소 얻기
    public String getFullUrl(String key) {
        return amazonS3.getUrl(bucket,key).toString();
    }

}
