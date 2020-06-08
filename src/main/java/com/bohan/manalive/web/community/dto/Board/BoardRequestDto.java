package com.bohan.manalive.web.community.dto.Board;

import com.bohan.manalive.web.community.domain.Board.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class BoardRequestDto {
    LocalDateTime currentDateTime = LocalDateTime.now();

    private Long seq;
    private String title;
    private String content;
    private String hashtags;
    private String email;
    private LocalDateTime createDate;


    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .hashtags(hashtags)
                .email(email)
                .createDate(currentDateTime)
                    .build();
    }



}
