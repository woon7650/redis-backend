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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @PostMapping(value = "/login")
    public ApiResponse<UserDto> login(@RequestBody UserDto userDto, HttpServletResponse httpServletResponse) throws Exception{
        UserDto responseUserDto = authService.login(userDto, httpServletResponse);
        return ApiResponse.success(ResponseCode.SELECT_SUCCESS.getHttpStatusCode(), null, responseUserDto);
    }


    @PostMapping(value = "/signup")
    public ApiResponse<UserDto> signup(@RequestBody UserDto userDto) throws Exception{
        UserDto responseUserDto = authService.signup(userDto);
        return ApiResponse.success(ResponseCode.INSERT_SUCCESS.getHttpStatusCode(), null, responseUserDto);
    }

    @PostMapping(value = "/reissue")
    public ApiResponse<UserDto> reissue(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception{
        UserDto responseUserDto = authService.reissue(httpServletRequest, httpServletResponse);
        return ApiResponse.success(ResponseCode.INSERT_SUCCESS.getHttpStatusCode(), null, responseUserDto);
    }

    @PostMapping(value = "/logout")
    public ApiResponse<?> logout(HttpServletRequest httpServletRequest) throws Exception{
        authService.logout(httpServletRequest);
        return ApiResponse.success(ResponseCode.INSERT_SUCCESS.getHttpStatusCode(), null, null);
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
