package com.bohan.manalive.web.community.domain.Board;

import com.bohan.manalive.config.oauth.dto.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToOne(targetEntity= Board.class, fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(referencedColumnName = "seq", name="b_seq", nullable = false, insertable = false, updatable = false)
    private Board replyBoard;

    @Builder
    public BoardReply(Long b_seq, String content, String replyer) {
        this.b_seq = b_seq;
        this.content = content;
        this.replyer = replyer;
    }








}
