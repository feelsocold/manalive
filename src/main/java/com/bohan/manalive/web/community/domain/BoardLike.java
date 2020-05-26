package com.bohan.manalive.web.community.domain;

import com.bohan.manalive.config.oauth.dto.BaseTimeEntity;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@RequiredArgsConstructor
@Entity
public class BoardLike extends BaseTimeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(nullable = false)
    private Long b_seq;

    @Column(nullable = false)
    private String email;

    @Builder
    public BoardLike(Long b_seq, String email) {
        this.b_seq = b_seq;
        this.email = email;
    }



}
