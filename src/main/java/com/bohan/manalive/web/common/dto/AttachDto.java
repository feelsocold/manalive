package com.bohan.manalive.web.common.dto;

import com.bohan.manalive.web.common.domain.attach.Attach;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class AttachDto implements Serializable {

    private Long att_no;
    private String category;
    private Long superKey;
    private String filename;
    private String extension;
    private String uuid;
    private String url;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    public AttachDto(Long att_no, String url) {
        this.att_no = att_no;
        this.url = url;
    }

    public AttachDto(Long att_no, String category, Long superKey, String filename, String extension, String uuid, String url, LocalDateTime createDate, LocalDateTime modifiedDate){
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

    public AttachDto(String filename, String extension, String uuid, String category, String url){
        this.filename = filename;
        this.extension = extension;
        this.uuid = uuid;
        this.category = category;
        this.url = url;
    }

    @Builder
    public AttachDto(String filename, Long superKey, String extension, String uuid, String category, String url){
        this.filename = filename;
        this.category = category;
        this.superKey = superKey;
        this.extension = extension;
        this.uuid = uuid;
        this.url = url;
    }

    public Attach toEntity() {
        return Attach.builder()
                .category(category)
                .superKey(superKey)
                .filename(filename)
                .extension(extension)
                .uuid(uuid)
                .url(url)
                    .build();
    }

}
