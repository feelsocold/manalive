package com.bohan.manalive.web.community.domain.UserMarket;

import com.bohan.manalive.web.community.domain.Market.Market;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class UserMarketFollow implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(nullable = false)
    private Long userMarketSeq;

    @Column(nullable = false)
    private Long followerMarketSeq;

//    @OneToMany(mappedBy="userMarket", fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    @OrderBy("seq DESC")
//    private List<Market> marketList = new ArrayList<>();

    @ManyToOne(targetEntity= UserMarket.class, fetch=FetchType.LAZY, cascade = CascadeType.DETACH)
    @JsonBackReference
    @JoinColumn(referencedColumnName = "seq", name="userMarketSeq", nullable = false, insertable = false, updatable = false)
    private UserMarket followingUserMarket;

    @Builder
    public UserMarketFollow(Long userMarketSeq, Long followerMarketSeq) {
        this.userMarketSeq = userMarketSeq;
        this.followerMarketSeq = followerMarketSeq;
    }

//    public Market update(String title, String content, String hashtags, LocalDateTime modifiedDate ){
//        this.title = title;
//        this.content = content;
//        this.hashtags = hashtags;
//        this.modifiedDate = modifiedDate;
//        return this;
//    }

//    public Market update(int readCount){
//        this.readCount = readCount;
//        return this;
//    }
}
