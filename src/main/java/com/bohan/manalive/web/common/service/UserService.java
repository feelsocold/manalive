package com.bohan.manalive.web.common.service;

import com.bohan.manalive.config.oauth.dto.RegisterUser;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.domain.user.Role;
import com.bohan.manalive.domain.user.User;
import com.bohan.manalive.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    @Autowired
    private PasswordEncoder encoder;

    private final HttpSession httpSession;
    private final UserRepository userRepository;

    public Long standardRegister(RegisterUser registerUser) {
        return userRepository.save(User.builder()
                                    .email(registerUser.getEmail())
                                    .name(registerUser.getName())
                                    .password(encoder.encode(registerUser.getPassword()))
                                    .nickname(registerUser.getNickname())
                                    .phone(registerUser.getPhone())
                                    .enable("1")
                                    .role(Role.USER)
                                    .build()).getSeq();
    }

    @Transactional
    public void socialRegister(RegisterUser registerUser) {
        User user = userRepository.findByEmail(registerUser.getEmail())
                .map(entity -> entity.update(registerUser.getName(),
                                             registerUser.getNickname(),
                                             registerUser.getPhone(),
                                             "1",
                                             Role.USER )).orElse(registerUser.toEntity());

        httpSession.setAttribute("user", new SessionUser(user));
    }

    public Boolean duplicateCheck(String email) {
        boolean bool;
        //User user = userRepository.findByEmail(email).orElse(null);
        Optional<User> userOpt =  userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userRepository.findByEmail(email).get();
            System.out.println(user.getRole().getKey());
            System.out.println(user.getName());

            bool = true;
            System.out.println("!!!!!!!!! + " + bool);
            return bool;
        }else {

            bool = false;
            System.out.println("!!!!!!!!! + " + bool);
            return bool;
        }
    }

}
