package com.bohan.manalive.web.common.dto;

import com.bohan.manalive.web.common.domain.attach.Attach;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class AttachResponseDto implements Serializable {

    private Long att_no;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private String category;
    private String extension;
    private String filename;
    private Long superKey;
    private String url;
    private String uuid;

    public AttachResponseDto(Long att_no, String url) {
        this.att_no = att_no;
        this.url = url;
    }

    public AttachResponseDto(Long att_no, String category, Long superKey, String filename, String extension, String uuid, String url, LocalDateTime createDate, LocalDateTime modifiedDate){
        this.att_no = att_no;
        this.category = category;
        this.superKey = superKey;
        this.filename = filename;
        this.extension = extension;
        this.uuid = uuid;
        this.url = url;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }

    public AttachResponseDto(Attach entity){

        this.att_no = entity.getAtt_no();
        this.createDate = entity.getCreateDate();
        this.modifiedDate = entity.getModifiedDate();
        this.category = entity.getCategory();
        this.superKey = entity.getSuperKey();
        this.extension = entity.getExtension();
        this.filename = entity.getFilename();
        this.uuid = entity.getUuid();
        this.url = entity.getUrl();


    }


}
