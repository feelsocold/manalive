package com.bohan.manalive.web.common.restController;

import com.bohan.manalive.domain.user.User;
import com.bohan.manalive.web.common.service.EmailService;
import com.bohan.manalive.web.common.service.LoginService;
import com.bohan.manalive.web.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final EmailService emailService;
    private final LoginService loginService;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(getClass());

    // 회원가입시 이메일로 인증번호 전송
    @PostMapping("/authentication_send")
    public int mailSend(HttpServletRequest req, String addrs, String to) throws MailException, MessagingException {
        logger.info("이메일 주소 : " +  to);
        int numberSix = emailService.sendSimpleMessage(to, "제목", "내용");
        return numberSix;
    }

    // 회원가입시 이메일 중복체크
    @PostMapping("/duplicate_check")
    public Boolean duplicateEmailCheck(String email) {
        boolean bool = userService.duplicateEmailCheck(email);
        return bool;
    }

    // 로그인시 아이디+비밀번호 확인
    @PostMapping("/loginCheck")
    public void loginCheck(User user) throws Exception {

        boolean bool = false;
        bool = loginService.loginCheck(user);

        logger.info("======> " + bool);
        //return false;
    }

    @GetMapping("/loginFail")
    public String loginFail(){
        return "로그인 실패!";
    }

    @PostMapping("/user/login")
    public void userLogin(String login_email, String login_password) throws Exception {
        logger.error("======> userLogin()");
    }

    // 휴대폰 본인인증시 번호 중복체크
    @PostMapping("/duplicatePhoneCheck")
    public Boolean duplicatePhoneCheck(String phone) {
        return userService.duplicatePhoneCheck(phone);
    }

    // 휴대폰 본인인증 번호 전송
    @PostMapping("/smsAuthentication_send")
    public int smsAuthentication_send(HttpServletRequest req, String addrs, String to) throws MailException, MessagingException {
        logger.info(to + "");
        double result = (int)Math.floor(Math.random() * 1000000)+100000;
        if(result>1000000){
            result = result - 100000;
        }
        return (int)Math.round(result);
    }

}
