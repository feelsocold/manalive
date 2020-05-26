package com.bohan.manalive.domain.user;

import com.bohan.manalive.config.oauth.dto.BaseTimeEntity;
import com.bohan.manalive.web.community.domain.Board;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
//@EqualsAndHashCode(callSuper = false, exclude = {"boardList"})
@ToString(exclude = "boardList")
@JsonIgnoreProperties
@Entity
public class User extends BaseTimeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String password;

    @Column(nullable = true)
    private String social;

    @Column(name="email", nullable = false)
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

//    @OneToMany(mappedBy="userDetail", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private List<Board> boardList = new ArrayList<>();
    @OneToMany(mappedBy="userDetail", fetch=FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonManagedReference
    private List<Board> boardList = new ArrayList<Board>();

//    @OneToOne(mappedBy = "userDetail")
//    private Board board;

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
