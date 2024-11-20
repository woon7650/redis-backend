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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("authService")
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public UserDto login(UserDto userDto, HttpServletResponse response) throws Exception{

        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() ->  new CustomException(ErrorCode.USER_NOT_FOUND));

        if(!passwordEncoder.matches(userDto.getPassword(), user.getPassword())){
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        updateRedisWithNewResponse(UserDto.of(user), response);

        return UserDto.of(user);
        //userRepository.login(userDto);
    }

    @Override
    @Transactional
    public UserDto signup(UserDto userDto) throws Exception{
        String encryptPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encryptPassword);
        User user = userRepository.save(userDto.toEntity());
        return UserDto.of(user);

    }

    @Override
    @Transactional
    public UserDto reissue(HttpServletRequest request, HttpServletResponse response) throws Exception{

        String accessToken = jwtUtil.resolveToken(request);
        String refreshToken = jwtUtil.getRefreshTokenFromCookie(request);


        /*
        Access Token에서 꺼낸 id로 redis에 있는 RefreshToken 검색
        RedisString 에 있는 RefreshToken과 Cookie에서 가져온 RefreshToken이 일치하는지 확인
        */
        String userId = jwtUtil.getUserIdFromClaims(accessToken);
        RefreshToken redisToken = refreshTokenRepository.findById(userId)
                .orElseThrow(() ->  new CustomException(ErrorCode.EXPIRED_TOKEN));


        //Refresh Token 유효성 검사
        if(!jwtUtil.validateToken(redisToken.getRefreshToken())){
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        if(!refreshToken.equals(redisToken.getRefreshToken())){
            //로그아웃
            throw new CustomException(ErrorCode.COUNTERFEIT);
        }

        /*
        유효성 검사와 동일 여부 확인을 모두 통과한 경우
        Access Token, Refresh Token 모두 재발행(RTR)
        */
        User user = userRepository.findById(userId)
                .orElseThrow(() ->  new CustomException(ErrorCode.UNAUTHORIZED));

        TokenDto responseTokenDto = updateRedisWithNewResponse(UserDto.of(user), response);

        return UserDto.of(user);
    }

    @Override
    public void logout(HttpServletRequest request) throws Exception{

        String accessToken = jwtUtil.resolveToken(request);
        if(refreshTokenRepository.findByAccessToken(accessToken).isEmpty()){
            //Refresh Token 유효성 검사
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        }

        refreshTokenRepository.deleteByAccessToken(accessToken);
        SecurityContextHolder.clearContext();

    }



    public TokenDto updateRedisWithNewResponse(UserDto userDto, HttpServletResponse response){
        //access, refresh token 생성
        final String accessToken = jwtUtil.generateAccessToken(userDto);
        final String refreshToken = jwtUtil.generateRefreshToken(userDto);

        //refresh token redis 저장
        refreshTokenRepository.save(new RefreshToken(userDto.getId(), accessToken, refreshToken));


        response.setHeader("Authorization", accessToken);
        response.setHeader("Set-Cookie", (jwtUtil.setCookieWithRefreshToken(refreshToken)).toString());

        //response 생성
        TokenDto tokenDto = TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();


        return tokenDto;
    }


}
