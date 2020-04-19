package com.bohan.manalive.config.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SecurityUser extends User {
    private static final long serialVersionUID = 1L;
    private com.bohan.manalive.domain.user.User user;

    public SecurityUser(com.bohan.manalive.domain.user.User user) {
        super(user.getEmail(), user.getPassword(),
                AuthorityUtils.createAuthorityList(user.getRole().getKey()));
    }

    public com.bohan.manalive.domain.user.User getUser() {
        return user;
    }


}
