package com.example.login.auth.service;

import com.example.login.auth.model.RefreshToken;
import com.example.login.auth.model.TokenDto;
import com.example.login.auth.repository.RefreshTokenRepository;
import com.example.login.common.enumType.ErrorCode;
import com.example.login.common.exception.CustomException;
import com.example.login.common.util.JwtUtil;
import com.example.login.user.dto.UserDto;
import com.example.login.user.model.User;
import com.example.login.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("authService")
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public TokenDto login(UserDto userDto) throws Exception{

        User user = userRepository.findById(userDto.getId()).orElseThrow(() ->  new CustomException(ErrorCode.USER_NOT_FOUND));

        if(!passwordEncoder.matches(userDto.getPassword(), user.getPassword())){
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        //access, refresh token 생성
        final String accessToken = jwtUtil.generateAccessToken(UserDto.of(user));
        final String refreshToken = jwtUtil.generateRefreshToken(UserDto.of(user));

        //refresh token redis 저장
        refreshTokenRepository.save(new RefreshToken(user.getId(), refreshToken));

        //response 생성
        TokenDto tokenDto = TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userDto(UserDto.of(user))
                .build();

        return tokenDto;
        //userRepository.login(userDto);
    }

    @Override
    public void logout(UserDto UserDto) throws Exception{

        if(refreshTokenRepository.findByRefreshToken(tokenDto.getRefreshToken()).isEmpty()){
            //Refresh Token 유효성 검사
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        refreshTokenRepository.deleteByRefreshToken(tokenDto.getRefreshToken());
        SecurityContextHolder.clearContext();

    }

    @Override
    public TokenDto reissue(UserDto userDto, TokenDto tokenDto) throws Exception{

        //Refresh Token 유효성 관련 없이 access, refresh token 모두 재발급(RTR)
        final String accessToken = jwtUtil.generateAccessToken(userDto);
        final String refreshToken = jwtUtil.generateRefreshToken(userDto);

        //refresh token redis 저장
        refreshTokenRepository.save(new RefreshToken(userDto.getId(), refreshToken));

        //response 생성
        TokenDto responsenTokenDto = TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userDto(userDto)
                .build();


        return responsenTokenDto;
        //userRepository.login(userDto);
    }

}
