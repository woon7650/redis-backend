package com.example.login.common.util;

import com.example.login.common.enumType.ErrorCode;
import com.example.login.common.exception.BusinessException;
import com.example.login.common.exception.CustomException;
import com.example.login.user.dto.UserDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@Transactional(Transactional.TxType.REQUIRED)
public class JwtUtil {

    @Value("${spring.jwt.token.access-expiration}")
    private Long accessExpirationTime;

    @Value("${spring.jwt.token.refresh-expiration}")
    private Long refreshExpirationTime;

    @Value("${spring.jwt.secretKey}")
    private String secretKey;

    private final Key key;

    public JwtUtil(@Value("${spring.jwt.secretKey}") String secretKey){
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }


    public String createToken(UserDto userDto, Long expirationTime){
        //signWith(
        Date now = new Date();
        return Jwts.builder()
                .setSubject(userDto.getId())
                .setHeader(createHeader())
                .setClaims(createClaims(userDto))
                .setExpiration(new Date(now.getTime() + expirationTime))
                .signWith(key)
                .compact();
    }

    public String generateAccessToken(UserDto userDto){
        return createToken(userDto, accessExpirationTime);
    }

    public String generateRefreshToken(UserDto userDto){
        return createToken(userDto, refreshExpirationTime);
    }

    public Map<String, Object> createHeader(){
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());
        return header;
    }


    /**
     * 'Claim'을 반환하는 메서드
     *
     * @param userDto : 사용자정보
     * @return Map : claims
     */

    private Map<String, Object> createClaims(UserDto userDto) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userDto.getId());
        claims.put("userEmail", userDto.getEmail());
        claims.put("userName", userDto.getName());
        return claims;
    }

    /**
     * '토큰 유효성 여부'를 반환하는 메서드
     *
     * @param jwtToken : 토큰
     * @return Boolean : 토큰 유효성 여부
     */
    public boolean validateToken(String jwtToken){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtToken);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    /**
     * '토큰' 내에서 'Claims'을 반환하는 메서드
     *
     * @param token : 토큰
     * @return Claims : Claims
     */

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 'Claims' 내에서 '사용자 아이디'를 반환하는 메서드
     *
     * @param token : 토큰
     * @return String : 사용자 아이디
     */
    public  String getUserIdFromClaims(String token) {
        Claims claims = parseClaims(token);
        return claims.get("userId").toString();
    }

    /**
     * 'Cookie' 내에서 'Refresh Token'를 저장하는 메서드
     *
     * @param refreshToken : 토큰
     * @return ResponseCookie : Cookie
     */
    public ResponseCookie setCookieWithRefreshToken(String refreshToken){

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .maxAge(refreshExpirationTime)
                .path("/")
                // secure(true) : https 환경에서만 쿠키가 발동합니다.
                .secure(false)
                .sameSite("None")
                .httpOnly(true)
                .build();
        return cookie;
    }

}
