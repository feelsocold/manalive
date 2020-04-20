package com.bohan.manalive.config.oauth.dto;

import com.bohan.manalive.domain.user.Role;
import com.bohan.manalive.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RegisterUser {

    private String email;
    private String name;
    private String password;
    //private String picture;
    private String nickname;
    private String phone;
    private String enable;
    private Role role;

    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
          //      .picture(picture)
                .phone(phone)
                .role(Role.USER)
                .enable("1")
                .build();
    }
}


