package com.alltruth.api.config.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@ToString
@Getter
public enum ErrorCode {
    // 400 BAD_REQUEST: 잘못된 요청
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    // 405 METHOD_NOT_ALLOWED: 허용되지 않은 Request Method 호출
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 요청입니다."),

    // 500 INTERNAL_SERVER_ERROR: 서버 에러
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다."),


    // 인증

    ACCESS_TOKEN_NOT_VALIDATE(HttpStatus.FORBIDDEN, "엑세스 토큰이 유효하지 않습니다."),

    // User
    UNAUTHENTICATION(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    UNAUTHORIZATION(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "회원이 존재하지 않습니다."),
    USER_NOT_MATCH(HttpStatus.BAD_REQUEST, "아이디 또는 비밀번호를 잘못 입력했습니다. 입력하신 내용을 다시 확인해주세요."),
    ID_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다."),
    PASSWORD_CONFIRM_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호 확인이 일치하지 않습니다."),

    // Review
    REVIEW_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 리뷰를 찾을 수 없습니다."),
    REVIEW_NOT_AUTHOR(HttpStatus.BAD_REQUEST, "해당 리뷰의 작성자가 아닙니다."),

    // Comment
    COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 리뷰를 찾을 수 없습니다."),
    COMMENT_NOT_AUTHOR(HttpStatus.BAD_REQUEST, "해당 리뷰의 작성자가 아닙니다.");


    private final HttpStatus status;
    private final String message;
}



