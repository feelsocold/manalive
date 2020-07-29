package com.bohan.manalive.web.community.dto.UserMarket;

import com.bohan.manalive.web.community.domain.UserMarket.UserMarket;
import com.bohan.manalive.web.community.domain.UserMarket.UserMarketFollow;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class UserMarketFollowRequestDto {

    private String email;
    private Long userMarketSeq;
    private Long followerMarketSeq;

    public UserMarketFollow toEntity() {
        return UserMarketFollow.builder()
                .userMarketSeq(userMarketSeq)
                .followerMarketSeq(followerMarketSeq)
                    .build();
    }
}
