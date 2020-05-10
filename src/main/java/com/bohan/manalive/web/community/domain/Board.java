package com.bohan.manalive.web.community.domain;

import com.bohan.manalive.config.oauth.dto.BaseTimeEntity;
import com.bohan.manalive.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@RequiredArgsConstructor
//@EqualsAndHashCode(callSuper = false, exclude = {"user"})
@ToString(exclude = "user")
@Entity
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private String hashtags;

    @Column(nullable = false, insertable=true, updatable=false)
    private String email;

//    @ManyToOne(targetEntity=User.class, fetch=FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "email", referencedColumnName = "email", insertable = false, updatable = false)
//    @JsonBackReference
//    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email", referencedColumnName = "email", insertable = false, updatable = false)
    private User userDetail;


    @Builder
    public Board(String title, String content, String hashtags, String email) {
        this.title = title;
        this.content = content;
        this.hashtags = hashtags;
        this.email = email;
    }
}
