package com.bohan.manalive.web.community.dto;

import com.bohan.manalive.domain.attach.Attach;
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

    public AttachSaveRequestDto(String filename, String extension, String uuid, String category){
        this.filename = filename;
        this.category = category;
        this.extension = extension;
        this.uuid = uuid;
    }

    @Builder
    public AttachSaveRequestDto(String filename, Long superKey, String extension, String uuid, String category){
        this.filename = filename;
        this.category = category;
        this.superKey = superKey;
        this.extension = extension;
        this.uuid = uuid;
    }

    public Attach toEntity() {

        return Attach.builder()
                .category(category)
                .superKey(superKey)
                .filename(filename)
                .extension(extension)
                .uuid(uuid)
                    .build();
    }

}
