package com.example.login.user.web;

import com.example.login.user.model.UserVO;
import com.example.login.user.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Resource(name = "userService")
    private UserService userService;

    @PostMapping(value = "/user/login")
    public void login(@RequestBody UserVO userVO) throws Exception{
        userService.login(userVO);
    }

    @PostMapping(value = "/user/signup")
    public void signup(@RequestBody UserVO userVO) throws Exception{
        userService.signup(userVO);
    }


}
