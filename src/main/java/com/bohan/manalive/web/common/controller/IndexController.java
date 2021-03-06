package com.bohan.manalive.web.common.controller;
import com.bohan.manalive.config.oauth.LoginUser;
import com.bohan.manalive.config.oauth.dto.RegisterUser;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.web.common.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexController {
    private final HttpSession httpSession;
    Logger logger = LoggerFactory.getLogger(getClass());
    //private final HttpSession httpSession;
    private final UserService userService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user, HttpServletRequest request, HttpServletResponse response) {
        logger.info("INDEX()");

        //SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null) {
            model.addAttribute("userName", user.getName());
            model.addAttribute("user", user);

            logger.info(user.getRole().getKey());

             if(user.getRole().getKey().equals("ROLE_GUEST")) {
//                 model.addAttribute("user", user);
                 return "redirect:/register";
             }else if(user.getRole().getKey().equals("ROLE_ADMIN")){
//                 model.addAttribute("user", user);

                 return "redirect:/admin";
             }
//            }else if(user.getRole().getKey().equals("ROLE_ADMIN")){
////                 model.addAttribute("user", user);
//                 return "admin/adminStart";
//             }
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
        log.info("register()");

        if(user != null) {
            model.addAttribute("user", user);
            if(user.getRole().getKey().equals("ROLE_GUEST")){
                httpSession.removeAttribute("user");
            }
        }
    }

    // 회원가입 비즈니스
    @PostMapping("/user_register")
    public String register(RegisterUser user, @RequestParam String oper) throws Exception {
        try{
            if(oper.equals("standard")){
                userService.standardRegister(user);
            }else if(oper.equals("social")) {
                log.info("^^^^^^ " + user.toString());
                log.info(user.getPhoto());
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

