package com.bohan.manalive.web.community.dto.UserMarket;

import com.bohan.manalive.web.community.domain.Market.Market;
import com.bohan.manalive.web.community.domain.UserMarket.UserMarket;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class UserMarketRequestDto {
    LocalDateTime currentDateTime = LocalDateTime.now();

    private String email;
    private String marketName;
    private String marketPhoto;
    private String phone;
    private int visitedGuestCnt;
    private LocalDateTime createDate;


    public UserMarket toEntity() {
        return UserMarket.builder()
                .email(email)
                .marketName(marketName)
                .marketPhoto(marketPhoto)
                .phone(phone)
                .createDate(currentDateTime)
                    .build();
    }



}
