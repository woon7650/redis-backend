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

        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() ->  new CustomException(ErrorCode.USER_NOT_FOUND));

        if(!passwordEncoder.matches(userDto.getPassword(), user.getPassword())){
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        TokenDto tokenDto= updateRedisWithNewResponse(UserDto.of(user));

        return tokenDto;
        //userRepository.login(userDto);
    }

    @Override
    public void logout(TokenDto tokenDto) throws Exception{

        if(refreshTokenRepository.findByRefreshToken(tokenDto.getRefreshToken()).isEmpty()){
            //Refresh Token 유효성 검사
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        refreshTokenRepository.deleteByRefreshToken(tokenDto.getRefreshToken());
        SecurityContextHolder.clearContext();

    }



    @Override
    public TokenDto reissue(TokenDto tokenDto) throws Exception{

        String accessToken = tokenDto.getAccessToken();
        String refreshtToken = tokenDto.getRefreshToken();

        //Refresh Token 유효성 검사
        if(!jwtUtil.validateToken(refreshtToken)){

            //로그아웃
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        /*
        Access Token에서 꺼낸 id로 redis에 있는 RefreshToken 검색
        RedisString 에 있는 RefreshToken과 Cookie에서 가져온 RefreshToken이 일치하는지 확인
        */
        String userId = jwtUtil.getUserIdFromClaims(accessToken);
        RefreshToken redisToken = refreshTokenRepository.findById(userId)
                .orElseThrow(() ->  new CustomException(ErrorCode.USER_NOT_FOUND));

        if(!redisToken.equals(redisToken.getRefreshToken())){
            //로그아웃
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        /*
        유효성 검사와 동일 여부 확인을 모두 통과한 경우
        Access Token, Refresh Token 모두 재발행(RTR)
        */
        User user = userRepository.findById(userId)
                .orElseThrow(() ->  new CustomException(ErrorCode.USER_NOT_FOUND));
        TokenDto responseTokenDto = updateRedisWithNewResponse(UserDto.of(user));




        return responseTokenDto;
        //userRepository.login(userDto);
    }


    public TokenDto updateRedisWithNewResponse(UserDto userDto){
        //access, refresh token 생성
        final String accessToken = jwtUtil.generateAccessToken(userDto);
        final String refreshToken = jwtUtil.generateRefreshToken(userDto);

        //refresh token redis 저장
        refreshTokenRepository.save(new RefreshToken(userDto.getId(), refreshToken));

        //response 생성
        TokenDto tokenDto = TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        return tokenDto;
    }

}
