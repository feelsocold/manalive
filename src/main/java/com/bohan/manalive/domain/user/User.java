package com.bohan.manalive.domain.user;

import com.bohan.manalive.config.oauth.dto.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;


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

    @Column(nullable=true)
    private String photo;

    @Builder
    public User(String email,String name, String photo, Role role, String social, String enable, String nickname, String phone, String password) {
        this.email = email;
        this.name = name;
        this.photo = photo;
        this.role = role;
        this.social = social;
        this.enable = enable;
        this.nickname = nickname;
        this.phone = phone;
        this.password = password;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .photo(photo)
                .social(social)
                .nickname(nickname)
                .role(Role.USER)
                .enable(enable)
                .build();
    }

    public User update(String name){
        this.name = name;
        //this.photo = photo;
        return this;
    }

    public User update(String name, String nickname, String phone, String enable, Role role, String photo) {
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.enable = enable;
        this.role = role;
        this.photo = photo;

        return this;
    }

}
