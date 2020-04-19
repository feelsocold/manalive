package com.bohan.manalive.web.common.service.impl;

import com.bohan.manalive.domain.user.User;
import com.bohan.manalive.domain.user.UserRepository;
import com.bohan.manalive.web.common.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private PasswordEncoder encoder;

    private final UserRepository userRepository;

    public Boolean loginCheck(User loginUser) throws Exception{
        Boolean bool = false;

        Optional<User> optUser = userRepository.findByEmail(loginUser.getEmail());

        if(!optUser.isPresent()) {
            throw new UsernameNotFoundException(loginUser.getEmail() + " 사용자 없음");
        }else {
            User user = optUser.get();
            System.out.println(encoder.encode(loginUser.getPassword()));
            System.out.println(user.getPassword());

            if(loginUser.getPassword() == user.getPassword()) {
                bool = true;
            }

        }
        return bool;
    }

}
