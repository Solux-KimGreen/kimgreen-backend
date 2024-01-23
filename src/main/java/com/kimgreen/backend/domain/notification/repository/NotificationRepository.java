package com.kimgreen.backend.domain.notification.repository;

import com.kimgreen.backend.domain.member.entity.Member;
import com.kimgreen.backend.domain.notification.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    Page<Notification> findAllByMember(Member member, Pageable pageable);
}
