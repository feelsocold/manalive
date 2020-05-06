package com.bohan.manalive.domain.attach;

import com.bohan.manalive.config.oauth.dto.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

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

    @Builder
    public Attach(String filename, String category, Long superKey, String extension, String uuid) {
        this.filename = filename;
        this.category = category;
        this.superKey = superKey;
        this.extension = extension;
        this.uuid = uuid;
    }

}
