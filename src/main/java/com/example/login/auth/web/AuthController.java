package com.example.login.auth.web;

import com.example.login.auth.model.TokenDto;
import com.example.login.auth.service.AuthService;
import com.example.login.common.enumType.ResponseCode;
import com.example.login.common.response.ApiResponse;
import com.example.login.common.util.RedisUtils;
import com.example.login.user.dto.UserDto;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/auth/login")
    public ApiResponse<TokenDto> login(@RequestBody UserDto userDto, HttpServletRequest httpServletRequest) throws Exception{
        TokenDto tokenDto = authService.login(userDto);
        return ApiResponse.success(ResponseCode.SELECT_SUCCESS.getHttpStatusCode(), null, tokenDto);
    }

    @PostMapping(value = "/auth/logout")
    public ApiResponse<?> logout(@RequestBody UserDto userDto, HttpServletRequest httpServletRequest) throws Exception{
        authService.logout(userDto);
        return ApiResponse.success(ResponseCode.INSERT_SUCCESS.getHttpStatusCode(), null, userDto);
    }

    /*
    @PostMapping(value = "/redis/set")
    public ApiResponse<UserDto> addRedisKey(@RequestBody UserDto userDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception{
        redisUtils.setData(userDto.getId(), userDto.getName(), 100000L);
        return ApiResponse.success(ResponseCode.INSERT_SUCCESS.getHttpStatusCode(), null, userDto);
    }

    @PostMapping(value = "/redis/get")
    public ApiResponse<String> getRedisValue(@RequestBody UserDto userDto) throws Exception{

        String userName = redisUtils.getData(userDto.getId());
        return ApiResponse.success(ResponseCode.SELECT_SUCCESS.getHttpStatusCode(), null, userName);
    }
    */



}
