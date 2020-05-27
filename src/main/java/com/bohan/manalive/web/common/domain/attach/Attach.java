package com.bohan.manalive.web.common.domain.attach;

import com.bohan.manalive.config.oauth.dto.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@RequiredArgsConstructor
@Entity
public class Attach extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long att_no;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Long superKey;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = true)
    private String extension;

    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false, updatable = true)
    private String url;

    @Builder
    public Attach(String filename, String category, Long superKey, String extension, String uuid, String url) {
        this.filename = filename;
        this.category = category;
        this.superKey = superKey;
        this.extension = extension;
        this.uuid = uuid;
        this.url = url;
    }

    public Attach update(String filename, String extension, String uuid, String url) {
        this.filename = filename;
        this.extension = extension;
        this.uuid = uuid;
        this.url = url;
        return this;
    }

}
