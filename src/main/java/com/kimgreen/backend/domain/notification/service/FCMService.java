package com.kimgreen.backend.domain.notification.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.kimgreen.backend.domain.notification.entity.FCMToken;
import com.kimgreen.backend.domain.notification.repository.FCMTokenRepository;
import com.kimgreen.backend.domain.member.entity.Member;
import com.kimgreen.backend.domain.member.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class FCMService {
    @Value("${fcm.key.path}")
    private String FCM_PRIVATE_KEY_PATH;

    // 권한 설정
    @Value("${fcm.key.scope}")
    private String fireBaseScope;

    @Value(("{fcm.url}"))
    private String FCM_URL;

    private final FCMTokenRepository fcmTokenRepository;
    private final MemberRepository memberRepository;

    // fcm 기본 설정 진행
    // 이 과정에서 access token은 firebase가 자동 처리
    @PostConstruct
    public void init() {
        try {
            GoogleCredentials googleCredentials = GoogleCredentials
                    .fromStream(new ClassPathResource(FCM_PRIVATE_KEY_PATH).getInputStream())
                    .createScoped(List.of(fireBaseScope));

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(googleCredentials)
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @param senderId : 댓글 단 사람의 email
     * @param receiverId : 푸쉬 알림을 받을 클라이언트의 email -> FCM token 식별
     * @param content  : 댓글 내용
     * */
    @Transactional
    @Async
    public void   sendMessageTo(String senderId, String receiverId, String content) throws IOException{
        final FCMToken fcmToken = fcmTokenRepository.findByReceiverId(receiverId);
        Message message = makeMessage(fcmToken,senderId,receiverId,content);
        try {
            String messageResponse = FirebaseMessaging.getInstance().sendAsync(message).get();
            System.out.println(messageResponse);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        /*
        String message = makeMessage(fcmToken.getFcmToken(),senderId,receiverId,content);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(FCM_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer "+getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();

         */

    }

    public Message makeMessage(FCMToken token, String senderId, String receiverId, String content) {
        Member receiver = memberRepository.findByEmail(receiverId);
        Notification notification = new Notification("새 댓글이 작성되었습니다.", content);
        Message message = com.google.firebase.messaging.Message.builder()
                .setNotification(notification)
                .setToken(token.getFcmToken())
                .putData("senderId",senderId)
                .putData("receiverId",receiverId)
                .putData("receiverName",receiver.getNickname())
                .build();
        return message;
        /*
        //Notification
        Notification notification = new Notification("새 댓글이 작성되었습니다.", content);
        //Data
        Data messageData = new FCMMessageDto.Data(date,sender.getNickname(), senderId,receiverId, content);

        //Message : token + Notification + Data
        Message message = Message.builder()
                .token(FCMToken)
                .notification(notification)
                .data(messageData).build();
        //DTO : validate_only + Message
        FCMMessageDto fcmMessageDto = FCMMessageDto.builder()
                .validate_only(false)
                .message(message)
                .build();

        try {
            return objectMapper.writeValueAsString(fcmMessageDto);
        } catch (JsonProcessingException e) {
            throw new ConvertingJSONException();
        }

         */
    }
    @Transactional
    public void saveToken(Member member, String fcmToken) {
        if(!fcmTokenRepository.existsByReceiverId(member.getEmail())) {
            fcmTokenRepository.save(FCMToken.builder()
                    .fcmToken(fcmToken)
                    .receiverId(member.getEmail())
                    .build());
        } else {
            FCMToken tokenEntity = fcmTokenRepository.findByReceiverId(member.getEmail());
            tokenEntity.updateToken(fcmToken);
        }
    }


}
