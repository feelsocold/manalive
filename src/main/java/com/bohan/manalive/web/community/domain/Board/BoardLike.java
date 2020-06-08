package com.bohan.manalive.web.community.domain.Board;

import com.bohan.manalive.config.oauth.dto.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@ToString(exclude = "boardLikeList")
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

    @ManyToOne(targetEntity= Board.class, fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(referencedColumnName = "seq", name="b_seq", nullable = false, insertable = false, updatable = false)
    private Board likeBoard;

    @Builder
    public BoardLike(Long b_seq, String email) {
        this.b_seq = b_seq;
        this.email = email;
    }



}
