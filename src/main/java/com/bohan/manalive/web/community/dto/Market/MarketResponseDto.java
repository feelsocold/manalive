package com.bohan.manalive.web.community.dto.Market;

import com.bohan.manalive.web.common.domain.attach.Attach;
import com.bohan.manalive.web.community.domain.Market.Market;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MarketResponseDto {

    private Long seq;
    private String title;
    private String content;
    private String category;
    private String state;
    private String delivery;
    private int price;
    private int quantity;
    private int readCount;
    private LocalDateTime createDate;
    private String email;
    private List<Attach> attachList;

}
