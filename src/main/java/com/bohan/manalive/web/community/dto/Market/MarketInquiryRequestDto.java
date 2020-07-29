package com.bohan.manalive.web.community.dto.Market;

import com.bohan.manalive.web.community.domain.Market.MarketInquiry;
import com.bohan.manalive.web.community.domain.Market.MarketWish;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MarketInquiryRequestDto implements Serializable {


    private Long marketProductSeq;
    private Long userMarketSeq;
    private String content;
    private LocalDateTime createDate;

    public MarketInquiry toEntity() {
        return MarketInquiry.builder()
                .marketProductSeq(marketProductSeq)
                .userMarketSeq(userMarketSeq)
                .content(content)
                .createDate(createDate)
                .build();
    }


}
