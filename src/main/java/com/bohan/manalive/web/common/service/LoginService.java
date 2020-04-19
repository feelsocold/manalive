package com.bohan.manalive.web.common.service;

import com.bohan.manalive.domain.user.User;

public interface LoginService {

    public Boolean loginCheck(User user) throws Exception;

}
