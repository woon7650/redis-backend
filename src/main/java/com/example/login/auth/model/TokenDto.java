package com.example.login.auth.model;

import com.example.login.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TokenDto {

    String accessToken;
    String refreshToken;

}
