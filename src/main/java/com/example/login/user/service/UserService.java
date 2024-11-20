package com.example.login.user.service;


import com.example.login.user.dto.UserDto;
import com.example.login.user.model.User;

import java.beans.ExceptionListener;

public interface UserService {

    UserDto selectUserInfo(UserDto userDto) throws Exception;

}
