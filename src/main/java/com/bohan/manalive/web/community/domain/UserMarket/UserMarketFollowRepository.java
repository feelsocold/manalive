package com.bohan.manalive.web.community.domain.UserMarket;

import com.bohan.manalive.web.community.domain.Market.MarketWish;
import com.bohan.manalive.web.community.dto.UserMarket.UserMarketResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserMarketFollowRepository extends JpaRepository<UserMarketFollow, Long>,
                                         CrudRepository<UserMarketFollow, Long>{

    List<UserMarketFollow> findByUserMarketSeqAndAndFollowerMarketSeq(Long userMarketSeq, Long followerMarketSeq);
//    Long deleteByEmailAndUserMarketSeq(String email, Long userMarketSeq);
    Long deleteByUserMarketSeqAndFollowerMarketSeq(Long userMarketSeq, Long followerMarketSeq);


}
