package com.bohan.manalive.web.common.domain.attach;

import com.bohan.manalive.config.oauth.dto.BaseTimeEntity;
import com.bohan.manalive.web.community.domain.Board.Board;
import com.bohan.manalive.web.community.domain.Market.Market;
import com.bohan.manalive.web.community.domain.UserMarket.UserMarket;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.Constraint;

@ToString(exclude = "marketAttach, boardAttach")
@Getter
@RequiredArgsConstructor
@Entity
//@Table(
//        name="Attach",
//        uniqueConstraints=
//        @UniqueConstraint(columnNames={"superKey"})
//)
public class Attach extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attNo;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false, unique = false)
    private Long superKey;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = true)
    private String extension;

    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false, updatable = true)
    private String url;

//    @ManyToOne(targetEntity= UserMarket.class, fetch=FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonBackReference
//    @JoinColumn(name="superKey", referencedColumnName = "seq", nullable = false, insertable = false, updatable = false)
//    @Where(clause = "category = 'marketProfilePhoto'")
//    private UserMarket userMarketAttach;

//    @ManyToOne(targetEntity= Market.class, fetch=FetchType.EAGER, cascade = CascadeType.ALL)
//    @JsonBackReference
//    @JoinColumn(name="superKey", referencedColumnName = "seq", nullable = false, insertable = false, updatable = false)
//    @Where(clause = "category = 'marketPhoto'")
//    private Market marketAttach;
//
//    @ManyToOne(targetEntity= Board.class, fetch=FetchType.EAGER, cascade = CascadeType.ALL)
//    @JsonBackReference
//    @JoinColumn(name="superKey", referencedColumnName = "seq", nullable = false, insertable = false, updatable = false)
//    @Where(clause = "category = 'marketPhoto'")
//    private Board boardAttach;

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
