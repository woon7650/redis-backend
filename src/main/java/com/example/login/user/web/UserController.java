package com.example.login.user.web;

import com.example.login.common.enumType.ResponseCode;
import com.example.login.common.response.ApiResponse;
import com.example.login.user.dto.UserDto;
import com.example.login.user.model.User;
import com.example.login.user.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Resource(name = "userService")
    private UserService userService;

    @PostMapping(value = "/user/signup")
    public ApiResponse<UserDto> signup(@RequestBody UserDto userDto) throws Exception{
        return ApiResponse.success(ResponseCode.INSERT_SUCCESS.getHttpStatusCode(), null, UserDto.of(userService.signup(userDto)));
    }
}
