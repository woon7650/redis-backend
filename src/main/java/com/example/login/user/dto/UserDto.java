package com.example.login.user.dto;


import com.example.login.user.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDto {

    @JsonProperty
    private String id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty
    private String name;

    @JsonProperty
    private String email;



    public static UserDto of(User user){
        if(user == null){
            return null;
        }
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }


    public User toEntity(){
        return User.builder()
                .id(this.id)
                .email(this.email)
                .name(this.name)
                .password(this.password)
                .build();
    }
}
