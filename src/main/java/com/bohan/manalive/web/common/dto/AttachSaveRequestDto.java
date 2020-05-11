package com.bohan.manalive.web.common.dto;

import com.bohan.manalive.web.common.domain.attach.Attach;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class AttachSaveRequestDto implements Serializable {

    private String category;
    private Long superKey;
    private String filename;
    private String extension;
    private String uuid;
    private String url;

    public AttachSaveRequestDto(String filename, String extension, String uuid, String category, String url){
        this.filename = filename;
        this.extension = extension;
        this.uuid = uuid;
        this.category = category;
        this.url = url;
    }

    @Builder
    public AttachSaveRequestDto(String filename, Long superKey, String extension, String uuid, String category, String url){
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
