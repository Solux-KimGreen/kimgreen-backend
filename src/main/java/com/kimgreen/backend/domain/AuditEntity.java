package com.kimgreen.backend.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass //자식 클래스에게 매핑 정보만을 제공하고 싶을 때 사용하는 어노테이션
@EntityListeners(AuditingEntityListener.class)
//AuditingEntityListener 클래스가 callback listener로 지정되어 Entity에서 이벤트가 발생할 때마다 특정 로직을 수행
public class AuditEntity {

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @PrePersist
    public void prePersist() {
        String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.createdAt = LocalDateTime.parse(formattedDate,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.modifiedAt = this.createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.modifiedAt = LocalDateTime.parse(formattedDate,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}