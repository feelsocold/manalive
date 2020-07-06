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
    private String email;

    public MarketWish toEntity() {
        return MarketWish.builder()
                .email(email)
                .marketSeq(marketSeq)
                .createDate(currentDateTime)
                .build();
    }
}
