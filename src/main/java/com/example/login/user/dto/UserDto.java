package com.example.login.user.dto;


import com.example.login.user.model.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDto {

    private String id;

    private String password;

    private String name;

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
