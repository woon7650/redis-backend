package com.example.login.user.web;

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

    @PostMapping(value = "/user/login")
    public UserDto login(@RequestBody UserDto userDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception{
        return UserDto.of(userService.login(userDto));
    }

    @PostMapping(value = "/user/signup")
    public UserDto signup(@RequestBody UserDto userDto) throws Exception{
        return UserDto.of(userService.signup(userDto));
    }
}
