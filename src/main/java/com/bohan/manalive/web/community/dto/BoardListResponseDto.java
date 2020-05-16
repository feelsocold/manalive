package com.bohan.manalive.web.community.dto;

import com.bohan.manalive.web.community.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
public class BoardListResponseDto implements Serializable {

    private Long board_seq;
    private String title;
    private String content;
    private String hashtags;
    private String email;
    private LocalDateTime create_date;
    private LocalDateTime modified_date;

    private String nickname;
    private String photo;

    public BoardListResponseDto(Board entity) {
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.hashtags = entity.getHashtags();
        this.email = entity.getEmail();
        this.create_date = entity.getCreateDate();
        this.modified_date = getModified_date();
    }




}
