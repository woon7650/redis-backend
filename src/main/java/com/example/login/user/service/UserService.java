package com.example.login.user.service;


import com.example.login.user.dto.UserDto;
import com.example.login.user.model.User;

public interface UserService {

    User login(UserDto uservo) throws Exception;

    User signup(UserDto uservo) throws Exception;
}
