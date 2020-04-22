package com.bohan.manalive.config.oauth;

import com.bohan.manalive.config.oauth.dto.OAuthAttributes;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.domain.user.User;
import com.bohan.manalive.domain.user.UserRepository;
import com.bohan.manalive.web.community.domain.AttachSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    Logger logger = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        // 현재 로그인 진행 중인 서비스를 구분하는 코드
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // OAuth 로그인 진행 시 키가 되는 필드값 (구글의 기본 코드 : sub)
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        // OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey() );
    }

    private User saveOrUpdate(OAuthAttributes attributes) throws NoSuchElementException {
        if(userRepository.findByEmail(attributes.getEmail()).isPresent()){
            logger.info("아이디 존재!");
        }else {
            logger.info("아이디 XXXXXXXXXXXXXXX");
        }
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName()))
                //.map(entity -> entity.update(attributes.getName()))
                .orElse(attributes.toEntity());

        setSessionAttach(attributes.getPicture());

        return userRepository.save(user);
    }

    private void setSessionAttach(String filename) {
        UUID uuid = UUID.randomUUID();
        AttachSaveRequestDto attach = new AttachSaveRequestDto(filename, null, uuid.toString(), "profilePhoto");
        httpSession.setAttribute("attachDto", attach);
    }



}