package com.bohan.manalive.web.community.dto;

import com.bohan.manalive.web.community.domain.Board;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class BoardRequestDto {

    private String title;
    private String content;
    private String hashtags;
    private String email;

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .hashtags(hashtags)
                .email(email)
                    .build();
    }



}
