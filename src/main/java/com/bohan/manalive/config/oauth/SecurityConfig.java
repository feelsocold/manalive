package com.bohan.manalive.config.oauth;

import com.bohan.manalive.config.security.CustomSecurityUserDetailsService;
import com.bohan.manalive.config.security.CustomUrlAuthenticationFailureHandler;
import com.bohan.manalive.config.security.CustomUrlAuthenticationSuccessHandler;
import com.bohan.manalive.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@EnableWebSecurity          // Spring Security 설정들을 활성화시킨다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSecurityUserDetailsService userDetailsService;
    private final HttpSession httpSession;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Bean
//    public DaoAuthenticationProvider getAuthenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsService);
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        return authenticationProvider;
//    }

    // 패스워드 인증방식 설정
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    // Except From Oauth & Security
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/profile");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        // Oauth(소셜) Security
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                // URL 별 권한 관리를 설정하는 옵션의 시작점
                .authorizeRequests()
                    .antMatchers("/**", "/main", "/register", "/test",
                            "/h2-console/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    .antMatchers("/admin/**").hasRole(Role.ADMIN.name())
                    //.anyRequest().permitAll()
                .and()
                    .logout()
                     .logoutSuccessUrl("/")
                .and()
                 //OAuth2 로그인 기능에 대한 여러 설정의 진입점
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);
        // 일반 Security
        http.userDetailsService(userDetailsService);
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/test/**").hasRole(Role.USER.name())
                    .antMatchers("/admin/**").hasRole(Role.ADMIN.name())
                .and()
                .formLogin()
                    .loginPage("/**")
                    .loginProcessingUrl("/user/login")
                    .usernameParameter("login_email")
                    .passwordParameter("login_password")
                    .successHandler(authenticationSuccessHandler())
                    .failureHandler(authenticationFailureHandler(httpSession))
                    .permitAll()
                .and()
                .logout().logoutUrl("/logout")
                    .invalidateHttpSession(true).logoutSuccessUrl("/");
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new CustomUrlAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(HttpSession httpSession) {
        return new CustomUrlAuthenticationFailureHandler(httpSession);
    }
}
