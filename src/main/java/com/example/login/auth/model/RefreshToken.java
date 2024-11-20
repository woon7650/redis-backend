package com.example.login.auth.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "RefreshToken", timeToLive = 604800000)
public class RefreshToken {

    @Id
    private String id;

    @Indexed
    private String accessToken;

    private String refreshToken;

    public RefreshToken(String userId, String accessToken, String refreshToken){
        this.id = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
