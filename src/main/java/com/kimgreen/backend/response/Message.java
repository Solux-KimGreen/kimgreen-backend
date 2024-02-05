package com.kimgreen.backend.response;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.OK;

public class Message {
    public static String SIGN_UP_SUCCESS = "회원가입에 성공했습니다.";
    public static String LOG_IN_SUCCESS="로그인에 성공했습니다.";
    public static String TOKEN_REISSUE_SUCCESS="토큰 재발급에 성공했습니다.";
    public static String CHANGE_PASSWORD_SUCCESS="비밀번호 변경하기 성공했습니다.";
    public static String CHANGE_ALARM_SUCCESS="알림 설정 변경 성공했습니다.";
    public static String DELETE_MEMBER_SUCCESS="탈퇴하기 성공했습니다.";

    public static String WRITE_CERTIFY_POST_SUCCESS="인증 게시글 작성 성공했습니다.";
    public static String WRITE_DAILY_POST_SUCCESS="일상 게시글 작성 성공했습니다.";
    public static String GET_POST_LIST_SUCCESS="게시글 목록 불러오기 성공했습니다.";
    public static String GET_BEST_POST_LIST_SUCCESS="게시글 좋아요 상위목록 불러오기 성공했습니다.";
    public static String GET_POST_SUCCESS="게시글 상세 보기 성공했습니다.";
    public static String DELETE_POST_SUCCESS="게시글 삭제하기 성공했습니다.";
    public static String EDIT_POST_SUCCESS="게시글 수정하기 성공했습니다.";

    public static String CHANGE_NICKNAME_SUCCESS="닉네임 변경하기 성공했습니다.";
    public static String GET_MEMBER_INFO_SETTING_SUCCESS="설정창 정보 불러오기 성공했습니다.";
    public static String CHANGE_PROFILE_IMG_SUCCESS="프로필 사진 변경하기 성공했습니다.";
    public static String GET_MEMBER_INFO="사용자 별명 불러오기 성공했습니다.";
    public static String CHANGE_REPRESENTATIVE_BADGE_SUCCESS="대표뱃지 설정 성공했습니다.";
    public static String CHANGE_PROFILE_BADGE_SUCCESS="프로필뱃지 선택 성공했습니다.";
    public static String GET_COLLECTED_BADGE_INFO="획득뱃지 상세정보 불러오기 성공했습니다.";
    public static String GET_NOT_COLLECTED_BADGE_INFO="미획득 뱃지 상세정보 불러오기 성공했습니다.";
    public static String SUCCESS_GET_NOTIFICATION ="푸시알림 내역 보기 성공했습니다.";
    public static String POST_REPORT_SUCCESS="게시글 신고하기 성공했습니다.";
    public static String CALENDAR_SUCCESS="프로필 달력 불러오기 성공했습니다.";
    public static String CALENDAR_DETAILS_SUCCESS="프로필 달력 상세정보 불러오기 성공했습니다.";

    public static String POST_COMMENT_SUCCESS="댓글을 작성하는데 성공하였습니다.";
    public static String DELETE_COMMENT_SUCCESS="댓글 삭제 성공했습니다.";
    public static String GET_COMMENT_SUCCESS="댓글 목록을 불러오는데 성공하였습니다.";

    public static String SET_LIKES_SUCCESS="게시글 좋아요 or 취소하기 성공했습니다.";
    public static String PROFILE_INFO_SUCCESS="프로필 불러오기 성공했습니다.";
    public static String PROFILE_POSTS_SUCCESS="쓴 글 목록 불러오기 성공했습니다";

    public static String GET_MY_COMMENT_SUCCESS = "내가 쓴 댓글 보기 성공했습니다.";
    public static String GET_MY_POST_SUCCESS = "내가 쓴 글 보기 성공했습니다.";

}
