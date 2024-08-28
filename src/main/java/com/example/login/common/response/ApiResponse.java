package com.example.login.common.response;


import com.example.login.common.enumType.ErrorCode;
import com.example.login.common.enumType.ResponseCode;
import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;
    private String msg;

    private static final String SUCCESS = "success";

    private static final String FAIL = "fail";


    private ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(int code, String message, T data) {
        return new ApiResponse<T>(200, SUCCESS, data);
    }

    public static <T> ApiResponse<T> fail(ErrorCode errorCode, T data) {
        return new ApiResponse<T>(errorCode.getHttpStatusCode(), FAIL, data);
    }
}