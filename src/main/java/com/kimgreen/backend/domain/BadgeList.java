package com.kimgreen.backend.domain;

public enum BadgeList {
    MENTOR("그린 멘토",5),
    MENTEE("그린 멘티",5),
    EARLYBIRD("얼리버드",3),
    NORANG("김노랑",10),
    YEONDU("김연두",20),
    GREEN("김그린",50),
    RECEIPT_3("영수증도 전자로",3),
    RECEIPT_10("디지털 환경 지킴이",10),
    REUSABLE_3("re: user",3),
    REUSABLE_10("re: 마스터",10),
    PLASTIC_3("자연과 친구하기",3),
    PLASTIC_10("자연과 물아일체",10),
    PLOGGING_3("자연 속에 만 보 추구",3),
    PLOGGING_10("클린로드의 개척자",10),
    REFORM_3("손재주 아티스트",3),
    REFORM_10("친환경 대가",10),
    TRANSPORT_3("탄소 헤이터",3),
    TRANSPORT_10("지구 세이버",10),
    ETC_3("아마추어 기타리스트",3),
    ETC_10("프로 기타리스트",10),
    ADVENTURER("환경 모험가"),
    DOCTOR("김그린 박사"),
    GOLDEN_KIMGREEN("황금 김그린");

    private String name;
    private int goal;
    private String url;

    private BadgeList(String name, int goal) {
        this.name = name;
        this.goal = goal;
    }
    private BadgeList(String name) {
        this.name = name;
    }
    private BadgeList(String name, int goal, String url) {
        this.name = name;
        this.goal = goal;
        this.url = url;
    }

}
