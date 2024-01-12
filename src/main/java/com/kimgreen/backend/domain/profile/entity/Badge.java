package com.kimgreen.backend.domain.profile.entity;

import com.kimgreen.backend.domain.AuditEntity;
import com.kimgreen.backend.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Badge extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="badge_id")
    private Long memberId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @Column(name="mentor_count",columnDefinition = "int default 0")
    private int mentorCount;
    @Column(name="mentor_is_achieved",columnDefinition = "boolean default false")
    private boolean mentorIsAchieved;
    @Column(name="mentee_count",columnDefinition = "int default 0")
    private int menteeCount;
    @Column(name="mentee_is_achieved",columnDefinition = "boolean default false")
    private boolean menteeIsAchieved;

    @Column(name="certification_count",columnDefinition = "int default 0")
    private int certificationCount;
    @Column(name="certification_10_is_achieved",columnDefinition = "boolean default false")
    private boolean certification10IsAchieved;
    @Column(name="certification_20_is_achieved",columnDefinition = "boolean default false")
    private boolean certification20IsAchieved;
    @Column(name="certification_50_is_achieved",columnDefinition = "boolean default false")
    private boolean certification50IsAchieved;

    @Column(name="receipt_count",columnDefinition = "int default 0")
    private int receiptCount;
    @Column(name="receipt_3_is_achieved",columnDefinition = "boolean default false")
    private boolean receipt3IsAchieved;
    @Column(name="receipt_10_is_achieved",columnDefinition = "boolean default false")
    private boolean receipt10IsAchieved;

    @Column(name="reusable_count",columnDefinition = "int default 0")
    private int reusableCount;
    @Column(name="reusable_3_is_achieved",columnDefinition = "boolean default false")
    private boolean reusable3IsAchieved;
    @Column(name="reusable_10_is_achieved",columnDefinition = "boolean default false")
    private boolean reusable10IsAchieved;

    @Column(name="plastic_count",columnDefinition = "int default 0")
    private int plasticCount;
    @Column(name="plastic_3_is_achieved",columnDefinition = "boolean default false")
    private boolean plastic3IsAchieved;
    @Column(name="plastic_10_is_achieved",columnDefinition = "boolean default false")
    private boolean plastic10IsAchieved;

    @Column(name="plogging_count",columnDefinition = "int default 0")
    private int ploggingCount;
    @Column(name="plogging_3_is_achieved",columnDefinition = "boolean default false")
    private boolean plogging3IsAchieved;
    @Column(name="plogging_10_is_achieved",columnDefinition = "boolean default false")
    private boolean plogging10IsAchieved;

    @Column(name="reform_count",columnDefinition = "int default 0")
    private int reformCount;
    @Column(name="reform_3_is_achieved",columnDefinition = "boolean default false")
    private boolean reform3IsAchieved;
    @Column(name="reform_10_is_achieved",columnDefinition = "boolean default false")
    private boolean reform10IsAchieved;

    @Column(name="transport_count",columnDefinition = "int default 0")
    private int transportCount;
    @Column(name="transport_3_is_achieved",columnDefinition = "boolean default false")
    private boolean transport3IsAchieved;
    @Column(name="transport_10_is_achieved",columnDefinition = "boolean default false")
    private boolean transport10IsAchieved;

    @Column(name="etc_count",columnDefinition = "int default 0")
    private int etcCount;
    @Column(name="etc_3_is_achieved",columnDefinition = "boolean default false")
    private boolean etc3IsAchieved;
    @Column(name="etc_10_is_achieved",columnDefinition = "boolean default false")
    private boolean etc10IsAchieved;


    @Column(name="adventurer_is_achieved",columnDefinition = "boolean default false")
    private boolean adventurerIsAchieved;

    @Column(name="golden_is_achieved",columnDefinition = "boolean default false")
    private boolean goldenIsAchieved;

    String[] columnList = {
            "adventurer_is_achieved",
            "certification_10_is_achieved",
            "certification_20_is_achieved",
            "certification_50_is_achieved",
            "etc_10_is_achieved",
            "etc_20_is_achieved",
            "etc_50_is_achieved",
            "golden_is_achieved",
            "mentee_is_achieved",
            "mentor_is_achieved",
            "plastic_10_is_achieved",
            "plastic_20_is_achieved",
            "plastic_50_is_achieved",
            "plogging_10_is_achieved",
            "plogging_20_is_achieved",
            "plogging_50_is_achieved",
            "receipt_10_is_achieved",
            "receipt_20_is_achieved",
            "receipt_50_is_achieved",
            "reform_10_is_achieved",
            "reform_20_is_achieved",
            "reform_50_is_achieved",
            "reusable_10_is_achieved",
            "reusable_20_is_achieved",
            "reusable_50_is_achieved",
            "transport_10_is_achieved",
            "transport_20_is_achieved",
            "transport_50_is_achieved"
    };

}
