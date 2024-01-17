package com.kimgreen.backend.domain.community.entity;

public enum Reason {
    FAKE("거짓 정보"),
    ETC("기타");

    private String content;
    private Reason(String content) {
        this.content = content;
    }
}
