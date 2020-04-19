package com.bohan.manalive.web.common.controller;
import com.bohan.manalive.config.oauth.LoginUser;
import com.bohan.manalive.config.oauth.dto.RegisterUser;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.web.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final HttpSession httpSession;
    Logger logger = LoggerFactory.getLogger(getClass());
    //private final HttpSession httpSession;

    private final UserService userService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        logger.info("INDEX()");

        //SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if(user != null) {
            model.addAttribute("userName", user.getName());
            model.addAttribute("user", user);

            logger.info(user.getRole().getKey());
             if(user.getRole().getKey().equals("ROLE_GUEST")){
//                 model.addAttribute("user", user);
                return "redirect:/register";
            }
        }
        return "index";
    }

    @GetMapping("/main")
    public String main(Model model, @LoginUser SessionUser user) {
        model.addAttribute("user", user );
        return "index";
    }

    // 회원가입 창으로 이동
    @GetMapping("/register")
    public void register(Model model, @LoginUser SessionUser user) {

        if(user != null) {

            if(user.getRole().getKey().equals("ROLE_GUEST")){
                httpSession.removeAttribute("user");
            }
            model.addAttribute("user", user );
        }
    }

    // 회원가입 비즈니스
    @PostMapping("/user_register")
    public String register(RegisterUser user, @RequestParam String oper) throws Exception {
        try{
            if(oper.equals("standard")){
                userService.standardRegister(user);
            }else if(oper.equals("social")) {
                userService.socialRegister(user);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
            return "redirect:/";
    }

    @GetMapping("/test")
    public String test() {

        return "test";
    }



}

