package com.zero.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@Getter
@RequiredArgsConstructor
public enum ErrorEnum {
    NOT_FOUND(HttpStatus.UNAUTHORIZED, "관리자 권한이 없습니다."),
    NOT_MATCH_LINK(HttpStatus.BAD_REQUEST, "초대 링크가 일치하지 않습니다."),
    EXPIRED_LINK(HttpStatus.BAD_REQUEST, "존재하지 않는 링크입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorMsg;

}
