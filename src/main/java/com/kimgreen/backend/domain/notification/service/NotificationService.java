package com.kimgreen.backend.domain.notification.service;

import com.kimgreen.backend.domain.community.entity.Post;
import com.kimgreen.backend.domain.community.repository.PostRepository;
import com.kimgreen.backend.domain.member.entity.Member;
import com.kimgreen.backend.domain.member.repository.MemberRepository;
import com.kimgreen.backend.domain.member.service.MemberService;
import com.kimgreen.backend.domain.notification.dto.NotificationResponseDto;
import com.kimgreen.backend.domain.notification.entity.Notification;
import com.kimgreen.backend.domain.notification.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NotificationService {
    private final FCMService fcmService;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final NotificationRepository notificationRepository;
    private final MemberService memberService;


    //댓글 작성하기 API에서 호출
    @Transactional
    public void commentNotification(Long postId, String senderId, String receiverId,String content) {
        Member sender = memberRepository.findByEmail(senderId);
        Member receiver = memberRepository.findByEmail(receiverId);
        Optional<Post> post = postRepository.findById(postId);
        try{
            if(post.isPresent()) {
                fcmService.sendMessageTo(senderId, receiverId, content);
                notificationRepository.save(Notification.builder()
                                .member(receiver)
                                .content(content)
                                .postId(postId)
                                .build());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Transactional(readOnly = true)
    public Page<NotificationResponseDto> getNotifications(Pageable pageable) {
        Member member = memberService.getCurrentMember();
        Page<Notification> notifications = notificationRepository.findAllByMember(member,pageable);
        return notifications.map(NotificationResponseDto::getDto);
    }
}
