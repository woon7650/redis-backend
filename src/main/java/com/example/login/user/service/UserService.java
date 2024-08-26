package com.example.login.user.service;


import com.example.login.user.model.UserVO;

public interface UserService {

    void login(UserVO uservo) throws Exception;

    void signup(UserVO uservo) throws Exception;
}
