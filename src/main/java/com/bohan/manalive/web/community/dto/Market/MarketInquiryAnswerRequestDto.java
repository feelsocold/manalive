package com.bohan.manalive.web.community.dto.Market;

import com.bohan.manalive.web.community.domain.Market.MarketInquiry;
import com.bohan.manalive.web.community.domain.Market.MarketInquiryAnswer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor

public class MarketInquiryAnswerRequestDto implements Serializable {


    private Long inquirySeq;
    private String content;
    private LocalDateTime createDate;

    public MarketInquiryAnswer toEntity() {
        return MarketInquiryAnswer.builder()
                .marketInquirySeq(inquirySeq)
                .content(content)
                .createDate(createDate)
                .build();
    }


}
