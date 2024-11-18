package com.example.login.auth.service;

import com.example.login.auth.model.RefreshToken;
import com.example.login.auth.model.TokenDto;
import com.example.login.user.dto.UserDto;

public interface AuthService {

    TokenDto login(UserDto userDto) throws Exception;

    void logout(TokenDto tokenDto) throws Exception;

    TokenDto reissue(TokenDto tokenDto) throws Exception;


}
