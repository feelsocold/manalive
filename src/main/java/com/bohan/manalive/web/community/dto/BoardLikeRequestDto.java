package com.bohan.manalive.web.community.dto;

import com.bohan.manalive.web.community.domain.BoardLike;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class BoardLikeRequestDto implements Serializable {

    private Long b_seq;
    private String email;
    private String content;

    public BoardLike toEntity() {
        return BoardLike.builder()
                .b_seq(b_seq)
                .email(email)
                    .build();
    }




}
