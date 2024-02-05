package com.kimgreen.backend.domain.community.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kimgreen.backend.domain.AuditEntity;
import com.kimgreen.backend.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Post extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long postId;

    //ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    @JsonIgnore
    private Member member;

    //OneToOne
    @OneToOne(mappedBy = "post")
    private PostImg postImg;

    //OneToMany
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>();
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();


    @Column(length = 500, nullable = false)
    private String content;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private Tag tag;


    @Formula("(SELECT COUNT(*) FROM likes l WHERE l.post_id = post_id)")
    private int likeCount;

    public void update(String category, String content) {
        this.category = Category.valueOf(category);
        this.content = content;
    }
}
