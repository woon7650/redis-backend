package com.example.login.common.enumType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ResponseCode {

    //SUCCESS
    // 200 OK
    SELECT_SUCCESS(HttpStatus.OK, "S0", "조회 성공"),
    UPDATE_SUCCESS(HttpStatus.OK, "S1", "수정 성공"),
    INSERT_SUCCESS(HttpStatus.OK, "S2", "생성 성공"),
    DELETE_SUCCESS(HttpStatus.OK, "S3", "삭제 성공");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public int getHttpStatusCode() {

        return httpStatus.value();
    }

}