package com.kimgreen.backend.domain.community.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.kimgreen.backend.domain.community.entity.Post;
import com.kimgreen.backend.domain.community.entity.PostImg;
import com.kimgreen.backend.domain.community.repository.PostImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final PostImgRepository postImgRepository;
    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String saveFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename(); //사용자가 업로드한 파일명
        String createdFilename = createFileName(originalFilename); //겹치지 않게 랜덤 파일명 생성

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, createdFilename, multipartFile.getInputStream(), metadata);

        //return amazonS3.getUrl(bucket, createdFilename).toString(); //key?
        return createdFilename; //createdFilename = key : S3URL+key 형식으로 불러오면 됨
    }

    public void uploadDB(PostImg postImg) {
        postImgRepository.save(postImg);
    }

    public String createFileName(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        return UUID.randomUUID().toString()+fileExtension;
    }
}