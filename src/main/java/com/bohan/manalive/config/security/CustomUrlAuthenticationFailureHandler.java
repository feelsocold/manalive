package com.bohan.manalive.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RequiredArgsConstructor
public class CustomUrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final HttpSession httpSession;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        httpSession.removeAttribute("user");
        super.onAuthenticationFailure(request, response, exception);
    }

}
