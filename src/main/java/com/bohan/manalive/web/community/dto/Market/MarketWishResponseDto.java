package com.bohan.manalive.web.community.dto.Market;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MarketWishResponseDto implements Serializable {


    private Long wishSeq;
    private Long marketSeq;
    private String marketProductTitle;
    private LocalDateTime marketCreateDate;
    private Boolean marketProductIsSold;
    private int marketProductPrice;
    private int marketReadCount;
    private String marketMainPhoto;

//    private MarketWishResponseDto(Long wishSeq, Long marketSeq, String marketProductTitle, LocalDateTime marketCreateDate, Boolean marketProductIsSold, String marketMainPhoto){
//        this.wishSeq = wishSeq;
//        this.marketSeq = marketSeq;
//        this.marketProductTitle = marketProductTitle;
//        this.marketCreateDate = marketCreateDate;
//        this.marketProductIsSold = marketProductIsSold;
//        this.marketMainPhoto = marketMainPhoto;
//
//    }
//
//    private MarketWishResponseDto(Long wishSeq, Long marketSeq, String marketProductTitle, LocalDateTime marketCreateDate, Boolean marketProductIsSold){
//        this.wishSeq = wishSeq;
//        this.marketSeq = marketSeq;
//        this.marketProductTitle = marketProductTitle;
//        this.marketCreateDate = marketCreateDate;
//        this.marketProductIsSold = marketProductIsSold;
//        this.marketMainPhoto = marketMainPhoto;
//
//    }
//
//    private MarketWishResponseDto(String marketMainPhoto){
//        this.wishSeq = wishSeq;
//        this.marketSeq = marketSeq;
//        this.marketProductTitle = marketProductTitle;
//        this.marketCreateDate = marketCreateDate;
//        this.marketProductIsSold = marketProductIsSold;
//        this.marketMainPhoto = marketMainPhoto;
//
//    }

}
