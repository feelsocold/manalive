package com.bohan.manalive.web.community.dto.Board;

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
    private String email;

    public BoardReplyResponseDto(Long r_seq, String content, LocalDateTime createDate, String nickname, String photo, String email ){
        this.r_seq = r_seq;
        this.content = content;
        this.createDate = createDate;
        this.nickname = nickname;
        this.photo = photo;
        this.email = email;
    }











}
