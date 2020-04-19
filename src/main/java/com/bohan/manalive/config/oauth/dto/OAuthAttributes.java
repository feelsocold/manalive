package com.bohan.manalive.config.oauth.dto;

import com.bohan.manalive.domain.user.Role;
import com.bohan.manalive.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;


@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private String social;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey
            , String name, String email, String picture, String social) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.social = social;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName
            , Map<String, Object> attributes) {

        System.out.println(registrationId + " !!!!!!!!!!!!!!!!!!!!!!!!");

        if("naver".equals(registrationId)){
            return ofNaver("id", attributes, registrationId);
        }

        return ofGoogle(userNameAttributeName, attributes, registrationId);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes, String registrationId) {

        System.out.println((String) attributes.get("name"));
        System.out.println((String) attributes.get("email"));

        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .social(registrationId)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes, String registrationId){
        Map<String, Object> response = (Map<String, Object>)attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .social(registrationId)
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .social(social)
                .enable("0")
                .build();
    }
}