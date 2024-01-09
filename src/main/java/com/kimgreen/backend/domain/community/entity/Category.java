package com.kimgreen.backend.domain.community.entity;

public enum Category {
    RECEIPT("전자영수증 발급"),
    REUSABLE("리유저블 활동"),
    PLASTIC("플라스틱 프리"),
    PLOGGING("플로깅"),
    REFORM("리폼"),
    TRANSPORT("대중교통, 자전거"),
    ETC("기타");


    private String name;
    private Category(String name) {
        this.name=name;
    }
}
