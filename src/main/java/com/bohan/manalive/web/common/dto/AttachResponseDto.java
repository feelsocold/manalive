package com.bohan.manalive.web.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AttachResponseDto {

    private Long att_no;
    private String url;

    public AttachResponseDto(Long att_no, String url) {
        this.att_no = att_no;
        this.url = url;
    }


}
