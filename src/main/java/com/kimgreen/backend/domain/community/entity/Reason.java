package com.kimgreen.backend.domain.community.entity;

public enum Reason {
    WRONG_CATEGORY("잘못된 카테고리");

    private String content;
    private Reason(String content) {
        this.content = content;
    }
}
