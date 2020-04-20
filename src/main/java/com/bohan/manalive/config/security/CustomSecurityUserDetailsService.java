package com.bohan.manalive.config.security;

import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.domain.user.User;
import com.bohan.manalive.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.Session;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomSecurityUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    public UserRepository userRepo;

    private final HttpSession httpSession;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optional = userRepo.findByEmail(email);

        if(!optional.isPresent()) {
            throw new UsernameNotFoundException(email + " 사용자 없음");
        }else {
            User user = optional.get();

            httpSession.setAttribute("user", new SessionUser(user));

//            SessionUser user2 = (SessionUser)httpSession.getAttribute("user");
//            System.out.println(user.getEmail());


            return new SecurityUser(user);
        }
    }





}
