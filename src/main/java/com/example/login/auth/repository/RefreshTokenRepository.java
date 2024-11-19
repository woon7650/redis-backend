package com.example.login.auth.repository;

import com.example.login.auth.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByAccessToken(String accessToken);

    Optional<RefreshToken> findById(String id);

    Optional<RefreshToken> deleteByAccessToken(String accessToken);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

}
