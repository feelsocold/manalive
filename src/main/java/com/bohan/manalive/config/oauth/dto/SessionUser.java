package com.bohan.manalive.config.oauth.dto;

import com.bohan.manalive.domain.user.Role;
import com.bohan.manalive.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

//인증된 사용자 정보
@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String picture;
    private Role role;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.role = user.getRole();
    }

}