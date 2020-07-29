package com.bohan.manalive.web.community.dto.Market;

import com.bohan.manalive.web.community.domain.Market.MarketInquiry;
import com.bohan.manalive.web.community.domain.Market.MarketInquiryAnswer;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MarketInquiryResponseDto implements Serializable {


    private Long inquirySeq;
    private String content;
    private Long userMarketSeq;

    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    private String userMarketName;
    private String userMarketPhoto;

    //private List<MarketInquiryAnswer> inquiryAnswerList;


//    private MarketInquiryResponseDto(String content, LocalDateTime createDate, LocalDateTime modifiedDate, Long userMarketSeq, String userMarketName, String userMarketPhoto){
//
//    }

}
