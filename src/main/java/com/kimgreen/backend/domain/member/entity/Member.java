package com.kimgreen.backend.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kimgreen.backend.domain.AuditEntity;
import com.kimgreen.backend.domain.community.entity.Comment;
import com.kimgreen.backend.domain.community.entity.Likes;
import com.kimgreen.backend.domain.community.entity.Post;
import com.kimgreen.backend.domain.community.entity.Report;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Member extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long memberId;

    //OneToMany
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Likes> likes = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Report> reports = new ArrayList<>();


    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nickname;

    @Column(name="comment_alarm",columnDefinition = "boolean default false")
    private boolean commentAlarm;
    //@Column(name="like_alarm",columnDefinition = "boolean default false")
    //private boolean likeAlarm;
    @Enumerated(EnumType.STRING)
    private Role role;

    public void changePassword(String newPassword) {this.password = newPassword;}
    public void changeCommentAlarm(boolean b) {this.commentAlarm = b;}
    /*
    public void changeLikeAlarm(boolean b) {
        this.likeAlarm = b;
    }
     */
    public void changeNickname(String nickname) { this.nickname = nickname; }

}