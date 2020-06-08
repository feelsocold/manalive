package com.bohan.manalive.web.community.dto.Board;

import com.bohan.manalive.web.community.domain.Board.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class BoardListResponseDto implements Serializable {

    private Long seq;
    private String title;
    private String content;
    private String hashtags;
    private String email;
    private LocalDateTime create_date;
    private LocalDateTime modified_date;

    private String nickname;
    private String photo;

    public BoardListResponseDto(Long seq, String email, String nickname){
        this.seq = seq;
        this.email = email;
        this.nickname = nickname;
    }

    public BoardListResponseDto(Board entity) {
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.hashtags = entity.getHashtags();
        this.email = entity.getEmail();
        this.create_date = entity.getCreateDate();
        this.modified_date = getModified_date();
    }




}
