package com.example.login.common.enumType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //ERROR
    // 400 Bad Request
    // 403 Forbidden
    // 404 Not Found
    // 405 Method Not Allowed
    // 409 Conflict
    // 500 Internal Server Error

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "B0", "잘못된 요청입니다."),

    FORBIDDEN(HttpStatus.FORBIDDEN, "F0", "권한이 없습니다."),

    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "U0", "사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "U1", "비밀번호가 일치하지 않습니다."),
    USER_ALREADY_EXIST(HttpStatus.CONFLICT, "U2", "이미 가입한 사용자입니다."),

    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "M0", "허용되지 않은 메소드입니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S0", "서버에 오류가 발생하였습니다."),

    // Auth
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "A1", "인증이 필요합니다."),
    COUNTERFEIT(HttpStatus.UNAUTHORIZED, "A2", "위조 및 변조된 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "A3", "만료된 토큰입니다."),

    ;


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public int getHttpStatusCode() {

        return httpStatus.value();
    }


}