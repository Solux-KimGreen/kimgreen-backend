package com.kimgreen.backend.domain.notification.dto;

import com.kimgreen.backend.domain.notification.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@AllArgsConstructor
@Getter
public class NotificationResponseDto {
    private Long postId;
    private LocalDateTime createdAt;
    private String content;

    public static NotificationResponseDto getDto(Notification notification) {
        return NotificationResponseDto.builder()
                .content(notification.getContent())
                .postId(notification.getPostId())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
