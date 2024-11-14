package com.example.login.auth.model;

import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "refreshToken", timeToLive = 604800)
public class RefreshToken {

    @Id
    private String id;
    @Indexed
    private String refreshToken;

    public RefreshToken(String userId, String refreshToken){
        this.id = userId;
        this.refreshToken = refreshToken;
    }
}
