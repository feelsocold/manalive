package com.bohan.manalive.web.common.service;

import com.bohan.manalive.config.oauth.LoginUser;
import com.bohan.manalive.config.oauth.dto.RegisterUser;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.web.common.domain.attach.AttachRepository;
import com.bohan.manalive.domain.user.Role;
import com.bohan.manalive.domain.user.User;
import com.bohan.manalive.domain.user.UserRepository;
import com.bohan.manalive.web.common.dto.AttachDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    @Autowired
    private PasswordEncoder encoder;

    private final HttpSession httpSession;
    private final UserRepository userRepository;
    private final AttachRepository attachRepository;


    public void saveAttach(Long superKey) {
        List<AttachDto> attachList = (List<AttachDto>)httpSession.getAttribute("attachList");
        AttachDto requestDto = attachList.get(0);
        attachRepository.save(requestDto.toEntity().builder()
                                    .category(requestDto.getCategory())
                                    .superKey(superKey)
                                    .filename(requestDto.getFilename())
                                    .extension(requestDto.getExtension())
                                    .uuid(requestDto.getUuid())
                                    .url(requestDto.getUrl())
                                        .build());
    }

    @Transactional
    public void standardRegister(RegisterUser registerUser) {
        /*
        userRepository.save(User.builder()
                                .email(registerUser.getEmail())
                                .name(registerUser.getName())
                                .password(encoder.encode(registerUser.getPassword()))
                                .nickname(registerUser.getNickname())
                                .phone(registerUser.getPhone())
                                .enable("1")
                                .role(Role.USER)
                                //.picture(String.valueOf(att_no))
                                    .build()).getSeq(); */
        Long seq = userRepository.save(User.builder()
                                        .email(registerUser.getEmail())
                                        .name(registerUser.getName())
                                        .password(encoder.encode(registerUser.getPassword()))
                                        .nickname(registerUser.getNickname())
                                        .phone(registerUser.getPhone())
                                        .enable("1")
                                        .role(Role.USER)
                                        .photo(registerUser.getPhoto())
                                             .build()).getSeq();

        if (httpSession.getAttribute("attachList") != null) {
            saveAttach(seq);
        }

        User user = userRepository.findByEmail(registerUser.getEmail()).get().toEntity();
        httpSession.setAttribute("user", new SessionUser(user));
    }

    @Transactional
    public void socialRegister(RegisterUser registerUser) {
        User user = userRepository.findByEmail(registerUser.getEmail())
                .map(entity -> entity.update(registerUser.getName(),
                        registerUser.getNickname(),
                        registerUser.getPhone(),
                        "1",
                        Role.USER,
                        registerUser.getPhoto()

                        )).orElse(registerUser.toEntity());

        if (httpSession.getAttribute("attachList") != null) {
            saveAttach(user.getSeq());
        }
        httpSession.setAttribute("user", new SessionUser(user));
    }

    public Boolean duplicateEmailCheck(String email) {
        boolean bool;
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

    public Boolean duplicatePhoneCheck(String phone) {
        Optional<User> userOpt =  userRepository.findByPhone(phone);
        return (userOpt.isPresent()) ? true : false;
    }

    public void updatePhone(String email, String phone) {

        User user = userRepository.findByEmail(email)
                .map(entity -> entity.update(phone)).get();

        httpSession.setAttribute("user", new SessionUser(user));

    }


}