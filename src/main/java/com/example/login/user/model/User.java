package com.example.login.user.model;


import com.example.login.user.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "TB_USER")

public class User implements Serializable {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "PASSWORD")
    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "NAME")
    private String name;

    @Builder
    public User(String id, String email, String name, String password){
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }


}
