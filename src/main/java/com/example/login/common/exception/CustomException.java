package com.example.login.common.exception;

import com.example.login.common.enumType.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{

    ErrorCode errorCode;

}
