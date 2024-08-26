package com.example.login.user.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserVO {

    private String userId;

    //private String userName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userPassword;

    //private String accessToken;

    //private String refreshToken;
}
