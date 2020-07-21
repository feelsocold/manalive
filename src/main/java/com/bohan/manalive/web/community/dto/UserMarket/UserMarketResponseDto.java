package com.bohan.manalive.web.community.dto.UserMarket;

import com.bohan.manalive.web.community.domain.UserMarket.UserMarket;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor

public class UserMarketResponseDto {

    private Long seq;
    private String email;
    private String marketName;
    private String introduction;
    private String marketPhoto;
    private String phone;
    private int visitedGuestCnt;
    private LocalDateTime createDate;

    private String photoUrl;
    private Long followerCnt;
    private Long productCnt;

    public UserMarketResponseDto(Long seq, String marketName, String photoUrl){
        this.seq = seq;
        this.marketName = marketName;
        this.photoUrl = photoUrl;
    }

    public UserMarketResponseDto(Long seq, String marketName, String photoUrl, Long followerCnt, Long productCnt){
        this.seq = seq;
        this.marketName = marketName;
        this.photoUrl = photoUrl;
        this.followerCnt = followerCnt;
        this.productCnt = productCnt;
    }

    public UserMarketResponseDto(Long seq, String email, String marketName, String introduction, int visitedGuestCnt, LocalDateTime createDate, String photoUrl, Long followerCnt){
        this.seq = seq;
        this.email = email;
        this.marketName = marketName;
        this.introduction = introduction;
        this.visitedGuestCnt = visitedGuestCnt;
        this.createDate = createDate;
        this.photoUrl = photoUrl;
        this.followerCnt = followerCnt;
    }











}
