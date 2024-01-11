package com.kimgreen.backend.domain;

public enum BadgeList {
    BLANK(""),
    MENTOR("그린 멘토",5,"mentor.png"),
    MENTEE("그린 멘티",5,"mentee.png"),
    EARLYBIRD("얼리버드",3,"earlybird.png"),
    NORANG("김노랑",10,"norang.png"),
    YEONDU("김연두",20,"yeondu.png"),
    GREEN("김그린",50,"green.png"),
    RECEIPT_3("영수증도 전자로","electronic_receipt.png"),
    RECEIPT_10("디지털 환경 지킴이",10,"digital_protector.png"),
    REUSABLE_3("re: user",3,"re_user.png"),
    REUSABLE_10("re: 마스터",10,"re_master.png"),
    PLASTIC_3("자연과 친구하기",3,"eco_friend.png"),
    PLASTIC_10("자연과 물아일체",10,"eco_one.png"),
    PLOGGING_3("자연 속에 만 보 추구",3,"jamanchu.png"),
    PLOGGING_10("클린로드의 개척자",10,"clean_road.png"),
    REFORM_3("손재주 아티스트",3,"artist.png"),
    REFORM_10("친환경 대가",10,"eco_master.png"),
    TRANSPORT_3("탄소 헤이터",3,"C_hater.png"),
    TRANSPORT_10("지구 세이버",10),
    ETC_3("아마추어 기타리스트",3,"amateur_guitarist.png"),
    ETC_10("프로 기타리스트",10,"pro_guitarist.png"),
    ADVENTURER("환경 모험가","adventurer.png"),
    DOCTOR("김그린 박사","doctor.png"),
    GOLDEN_KIMGREEN("황금 김그린","golden_kimgreen.png");

    public String name;
    public int goal;
    public String url;


    private BadgeList(String name, int goal) {
        this.name = name;
        this.goal = goal;
    }
    private BadgeList(String name) {
        this.name = name;
    }
    private BadgeList(String name, String url) {
        this.name = name;
        this.url = url;
    }
    private BadgeList(String name, int goal, String url) {
        this.name = name;
        this.goal = goal;
        this.url = url;
    }

}
