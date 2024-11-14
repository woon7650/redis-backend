package com.example.login.user.service;


import com.example.login.user.dto.UserDto;
import com.example.login.user.model.User;

public interface UserService {

    User signup(UserDto uservo) throws Exception;
}
