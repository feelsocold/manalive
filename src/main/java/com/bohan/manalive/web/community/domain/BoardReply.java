package com.bohan.manalive.web.community.domain;

import com.bohan.manalive.config.oauth.dto.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@RequiredArgsConstructor
@Entity
public class BoardReply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long r_seq;

    @Column(nullable = false)
    private Long b_seq;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String replyer;

    @Builder
    public BoardReply(Long b_seq, String content, String replyer) {
        this.b_seq = b_seq;
        this.content = content;
        this.replyer = replyer;
    }








}
