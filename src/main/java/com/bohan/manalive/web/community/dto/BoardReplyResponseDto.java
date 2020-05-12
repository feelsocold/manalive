package com.bohan.manalive.web.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class BoardReplyResponseDto {
    //reply
    private Long r_seq;
    private Long b_seq;
    private String content;
    private LocalDateTime createDate;
    //user
    private String nickname;
    private String photo;

    public BoardReplyResponseDto(Long r_seq, String content, LocalDateTime createDate, String nickname, String photo ){
        this.r_seq = r_seq;
        this.content = content;
        this.createDate = createDate;
        this.nickname = nickname;
        this.photo = photo;
    }











}
