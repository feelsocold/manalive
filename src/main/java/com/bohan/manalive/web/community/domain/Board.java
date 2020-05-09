package com.bohan.manalive.web.community.domain;

import com.bohan.manalive.config.oauth.dto.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@RequiredArgsConstructor
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

    @Column(nullable = false)
    private String email;

    @Builder
    public Board(String title, String content, String hashtags, String email) {
        this.title = title;
        this.content = content;
        this.hashtags = hashtags;
        this.email = email;
    }
}
