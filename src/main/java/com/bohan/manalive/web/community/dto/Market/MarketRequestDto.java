package com.bohan.manalive.web.community.dto.Market;

import com.bohan.manalive.web.community.domain.Board.Board;
import com.bohan.manalive.web.community.domain.Market.Market;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class MarketRequestDto {
    LocalDateTime currentDateTime = LocalDateTime.now();

    private String title;
    private String content;
    private String category;
    private String state;
    private String delivery;
    private int price;
    private int quantity;
    private String email;
    private LocalDateTime createDate;


    public Market toEntity() {
        return Market.builder()
                .title(title)
                .content(content)
                .category(category)
                .state(state)
                .delivery(delivery)
                .price(price)
                .quantity(quantity)
                .email(email)
                .createDate(currentDateTime)
                    .build();
    }



}
