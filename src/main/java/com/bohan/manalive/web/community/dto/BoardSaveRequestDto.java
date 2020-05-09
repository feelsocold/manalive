package com.bohan.manalive.web.community.dto;

import com.bohan.manalive.web.community.domain.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BoardSaveRequestDto {

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
