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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource(name = "userService")
    private UserService userService;


    @PostMapping(value = "/select/info")
    public ApiResponse<UserDto> login(@RequestBody UserDto userDto) throws Exception{
        UserDto responseUserDto = userService.selectUserInfo(userDto);
        return ApiResponse.success(ResponseCode.SELECT_SUCCESS.getHttpStatusCode(), null, responseUserDto);
    }

}

