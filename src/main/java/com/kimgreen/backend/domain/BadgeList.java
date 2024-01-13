package com.kimgreen.backend.domain;

public enum BadgeList {
    BLANK("","",1),
    MENTOR("그린 멘토",5,"mentor.png","질문카테고리 댓글 5회 이상",2),
    MENTEE("그린 멘티",5,"mentee.png","질문 카테고리 글 작성 5회 이상",3),
    EARLYBIRD("얼리버드",3,"earlybird.png","9시 이전에 인증글 3회이상 작성",4),
    NORANG("김노랑",10,"norang.png","인증글 10회 이상 작성",5),
    YEONDU("김연두",20,"yeondu.png","인증글 20회 이상 작성",6),
    GREEN("김그린",50,"green.png","인증글 50회 이상 작성",7),
    RECEIPT_3("영수증도 전자로","electronic_receipt.png","전자영수증 발급 인증글 3회 이상",8),
    RECEIPT_10("디지털 환경 지킴이",10,"digital_protector.png","전자영수증 발급 인증글 10회 이상",9),
    REUSABLE_3("re: user",3,"re_user.png","리유저블 활동 인증글 3회 이상",10),
    REUSABLE_10("re: 마스터",10,"re_master.png","리유저블 활동 인증글 10회 이상",11),
    PLASTIC_3("자연과 친구하기",3,"eco_friend.png","플라스틱 프리 인증글 3회 이상",12),
    PLASTIC_10("자연과 물아일체",10,"eco_one.png","플라스틱 프리 인증글 10회 이상",13),
    PLOGGING_3("자연 속에 만 보 추구",3,"jamanchu.png","플로깅 인증글 3회 이상",14),
    PLOGGING_10("클린로드의 개척자",10,"clean_road.png","플로깅 인증글 10회 이상",15),
    REFORM_3("손재주 아티스트",3,"artist.png","리폼 인증글 3회 이상",16),
    REFORM_10("친환경 대가",10,"eco_master.png","리폼 인증글 10회 이상",17),
    TRANSPORT_3("탄소 헤이터",3,"C_hater.png","대중교통, 자전거 인증글 3회 이상",18),
    TRANSPORT_10("지구 세이버",10,"earth_saver.png","대중교통, 자전거 인증글 10회 이상",19),
    ETC_3("아마추어 기타리스트",3,"amateur_guitarist.png","기타 인증글 3회 이상",20),
    ETC_10("프로 기타리스트",10,"pro_guitarist.png","기타 인증글 10회 이상",21),
    ADVENTURER("환경 모험가","adventurer.png","각 카테고리별 인증글 1회 이상",22),
    DOCTOR("김그린 박사","doctor.png",23),
    GOLDEN_KIMGREEN("황금 김그린","golden_kimgreen.png","모든 뱃지 획득",24);

    public String name;
    public int goal;
    public String url;
    public String content;
    public int number;


    private BadgeList(String name, int goal,int number) {
        this.name = name;
        this.goal = goal;
    }
    private BadgeList(String name, String content,int number) {
        this.name = name;
        this.content = content;
    }
    private BadgeList(String name,int number) {
        this.name = name;
    }
    private BadgeList(String name, String url,String content, int number) {
        this.name = name;
        this.url = url;
        this.content = content;
    }
    private BadgeList(String name, int goal, String url,String content, int number) {
        this.name = name;
        this.goal = goal;
        this.url = url;
        this.content = content;
    }

}
