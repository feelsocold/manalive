package com.bohan.manalive.web.community.dto.Market;

import com.bohan.manalive.web.common.domain.attach.Attach;
import com.bohan.manalive.web.community.domain.Market.Market;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MarketResponseDto implements Serializable {

    private Long seq;
    private String title;
    private String content;
    private String category;
    private String productStatus;
    private String delivery;
    private int price;
    private int quantity;
    private int readCount;
    private LocalDateTime createDate;
    private String email;
    private List<Attach> attachList;

    private Long mSeq;
    private String mainPhotoUrl;

    public MarketResponseDto(Long m_seq, String title, String mainPhotoUrl){
        this.mSeq = m_seq;
        this.mainPhotoUrl = mainPhotoUrl;
        this.title = title;
    }

}
