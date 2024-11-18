package com.example.login.auth.service;

import com.example.login.auth.model.RefreshToken;
import com.example.login.auth.model.TokenDto;
import com.example.login.user.dto.UserDto;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    UserDto login(UserDto userDto, HttpServletResponse response) throws Exception;

    UserDto reissue(TokenDto tokenDto, HttpServletResponse response) throws Exception;

    void logout(TokenDto tokenDto) throws Exception;


}
