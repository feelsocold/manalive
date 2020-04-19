package com.bohan.manalive.domain.user;

import com.bohan.manalive.config.oauth.dto.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String password;

    @Column(nullable = true)
    private String social;

    @Column(nullable = false)
    private String email;

    @Column(nullable=true)
    private String picture;

    @Column(nullable = true)
    private String nickname;

    @Column(nullable = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = true)
    @ColumnDefault("1")
    String enable;

    @Builder
    public User(String name, String email, String picture, Role role, String social, String enable, String nickname, String phone, String password) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.social = social;
        this.enable = enable;
        this.nickname = nickname;
        this.phone = phone;
        this.password = password;
    }

    public User update(String name, String picture){
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .social(social)
                .nickname(nickname)
                .role(Role.USER)
                .enable(enable)
                .build();
    }

    public User update(String name, String nickname, String phone, String enable, Role role) {
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.enable = enable;
        this.role = role;

        return this;
    }

}
