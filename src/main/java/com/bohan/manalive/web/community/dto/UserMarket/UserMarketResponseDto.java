package com.bohan.manalive.web.community.dto.UserMarket;

import com.bohan.manalive.web.community.domain.UserMarket.UserMarket;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserMarketResponseDto {

    private Long seq;
    private String email;
    private String marketName;
    private String marketPhoto;
    private String phone;
    private int visitedGuestCnt;

    private String photoUrl;

    public UserMarketResponseDto(Long seq, String marketName, String photoUrl){
        this.seq = seq;
        this.marketName = marketName;
        this.photoUrl = photoUrl;
    }







}
