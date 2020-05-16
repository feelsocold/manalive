package com.bohan.manalive.web.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class AttachResponseDto {

    private Long att_no;
    private String category;
    private Long superKey;
    private String filename;
    private String extension;
    private String uuid;
    private String url;
    private LocalDateTime createDate;

    public AttachResponseDto(Long att_no, String url) {
        this.att_no = att_no;
        this.url = url;
    }

    public AttachResponseDto(Long att_no, String category, Long superKey, String filename, String extension, String uuid, String url, LocalDateTime createDate){
        this.att_no = att_no;
        this.category = category;
        this.superKey = superKey;
        this.filename = filename;
        this.extension = extension;
        this.uuid = uuid;
        this.url = url;
        this.createDate = createDate;
    }


}
