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
    @Column(name="receipt_10_is_achieved",columnDefinition = "boolean default false")
    private boolean receipt10IsAchieved;
    @Column(name="receipt_20_is_achieved",columnDefinition = "boolean default false")
    private boolean receipt20IsAchieved;
    @Column(name="receipt_50_is_achieved",columnDefinition = "boolean default false")
    private boolean receipt50IsAchieved;

    @Column(name="reusable_count",columnDefinition = "int default 0")
    private int reusableCount;
    @Column(name="reusable_10_is_achieved",columnDefinition = "boolean default false")
    private boolean reusable10IsAchieved;
    @Column(name="reusable_20_is_achieved",columnDefinition = "boolean default false")
    private boolean reusable20IsAchieved;
    @Column(name="reusable_50_is_achieved",columnDefinition = "boolean default false")
    private boolean reusable50IsAchieved;

    @Column(name="plastic_count",columnDefinition = "int default 0")
    private int plasticCount;
    @Column(name="plastic_10_is_achieved",columnDefinition = "boolean default false")
    private boolean plastic10IsAchieved;
    @Column(name="plastic_20_is_achieved",columnDefinition = "boolean default false")
    private boolean plastic20IsAchieved;
    @Column(name="plastic_50_is_achieved",columnDefinition = "boolean default false")
    private boolean plastic50IsAchieved;

    @Column(name="plogging_count",columnDefinition = "int default 0")
    private int ploggingCount;
    @Column(name="plogging_10_is_achieved",columnDefinition = "boolean default false")
    private boolean plogging10IsAchieved;
    @Column(name="plogging_20_is_achieved",columnDefinition = "boolean default false")
    private boolean plogging20IsAchieved;
    @Column(name="plogging_50_is_achieved",columnDefinition = "boolean default false")
    private boolean plogging50IsAchieved;

    @Column(name="reform_count",columnDefinition = "int default 0")
    private int reformCount;
    @Column(name="reform_10_is_achieved",columnDefinition = "boolean default false")
    private boolean reform10IsAchieved;
    @Column(name="reform_20_is_achieved",columnDefinition = "boolean default false")
    private boolean reform20IsAchieved;
    @Column(name="reform_50_is_achieved",columnDefinition = "boolean default false")
    private boolean reform50IsAchieved;

    @Column(name="transport_count",columnDefinition = "int default 0")
    private int transportCount;
    @Column(name="transport_10_is_achieved",columnDefinition = "boolean default false")
    private boolean transport10IsAchieved;
    @Column(name="transport_20_is_achieved",columnDefinition = "boolean default false")
    private boolean transport20IsAchieved;
    @Column(name="transport_50_is_achieved",columnDefinition = "boolean default false")
    private boolean transport50IsAchieved;

    @Column(name="etc_count",columnDefinition = "int default 0")
    private int etcCount;
    @Column(name="etc_10_is_achieved",columnDefinition = "boolean default false")
    private boolean etc10IsAchieved;
    @Column(name="etc_20_is_achieved",columnDefinition = "boolean default false")
    private boolean etc20IsAchieved;
    @Column(name="etc_50_is_achieved",columnDefinition = "boolean default false")
    private boolean etc50IsAchieved;


    @Column(name="adventurer_is_achieved",columnDefinition = "boolean default false")
    private boolean adventurerIsAchieved;

    @Column(name="golden_is_achieved",columnDefinition = "boolean default false")
    private boolean goldenIsAchieved;
}
