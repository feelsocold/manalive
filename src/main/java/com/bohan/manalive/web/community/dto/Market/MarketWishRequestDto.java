package com.bohan.manalive.web.community.dto.Market;

import com.bohan.manalive.web.community.domain.Market.Market;
import com.bohan.manalive.web.community.domain.Market.MarketWish;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class MarketWishRequestDto implements Serializable {

    LocalDateTime currentDateTime = LocalDateTime.now();
    private Long marketSeq;
    //private String email;
    private Long userMarketSeq;

    public MarketWish toEntity() {
        return MarketWish.builder()
                .marketSeq(marketSeq)
                .userMarketSeq(userMarketSeq)
                .createDate(currentDateTime)
                .build();
    }
}
