package com.bohan.manalive.web.community.dto;

import com.bohan.manalive.web.common.domain.attach.Attach;
import com.bohan.manalive.web.community.domain.BoardReply;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@RequiredArgsConstructor
public class BoardReplySaveRequestDto implements Serializable {

    private Long b_seq;
    private String content;
    private String replyer;

    public BoardReplySaveRequestDto(Long b_seq, String content, String replyer){
        this.b_seq = b_seq;
        this.content = content;
        this.replyer = replyer;
    }

    public BoardReply toEntity() {
        return BoardReply.builder()
                .b_seq(b_seq)
                .content(content)
                .replyer(replyer)
                .build();
    }
}
