package com.bohan.manalive.web.community.dto.Board;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class BoardResponseDto {

    private Long seq;
    private String title;
    private String content;
    private String hashtags;
    private int readCount;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;


    private String nickname;
    private String photo;
    private String email;

    public BoardResponseDto(Long seq, String title, String content, String hashtags, int readCount,LocalDateTime createDate, LocalDateTime modifiedDate, String nickname, String photo, String email){
        this.seq = seq;
        this.title = title;
        this.content = content;
        this.hashtags = hashtags;
        this.readCount = readCount;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
        this.nickname = nickname;
        this.photo = photo;
        this.email = email;

    }





}
