package com.bohan.manalive.web.community.domain.UserMarket;

import com.bohan.manalive.web.common.domain.attach.Attach;
import com.bohan.manalive.web.community.domain.Board.BoardLike;
import com.bohan.manalive.web.community.domain.Market.Market;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(callSuper = false, exclude = {"user"})
@ToString(exclude = "marketList, attachList2")
@Entity
public class UserMarket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String marketName;

    @Column(nullable = false)
    private String introduction;

    @Column(nullable = false)
    private String marketPhoto;

    @Column(nullable = false)
    private String phone;

    @Column(insertable = false)
    @ColumnDefault("0")
    private int visitedGuestCnt;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = true)
    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy="userMarket", fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("seq DESC")
    private List<Market> marketList = new ArrayList<>();

    @OneToMany(mappedBy="followingUserMarket", fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("seq DESC ")
    private List<UserMarketFollow> userMarketFollowList = new ArrayList<>();

//    @OneToMany(mappedBy="likeBoard", fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<BoardLike> boardLikeList = new ArrayList<>();

    @Builder
    public UserMarket(String email, String marketName, String introduction, String marketPhoto, String phone, LocalDateTime createDate) {
        this.email = email;
        this.introduction = introduction;
        this.marketName = marketName;
        this.marketPhoto = marketPhoto;
        this.phone = phone;
        this.createDate = createDate;
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
