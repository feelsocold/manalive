package com.bohan.manalive.web.community.domain.Market;

import com.bohan.manalive.web.common.dto.AttachResponseDto;
import com.bohan.manalive.web.community.domain.Board.Board;
import com.bohan.manalive.web.community.dto.Board.BoardResponseDto;
import com.bohan.manalive.web.community.dto.Market.MarketResponseDto;
import com.bohan.manalive.web.community.dto.UserMarket.UserMarketManageResponseDto;
import com.bohan.manalive.web.community.dto.UserMarket.UserMarketResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MarketRepository extends JpaRepository<Market, Long>,
                                         CrudRepository<Market, Long>,
                                         JpaSpecificationExecutor<Market>,
                                         QuerydslPredicateExecutor<Market> {

    @Query(" SELECT a.title FROM Market AS a " +
            " WHERE a.title LIKE CONCAT('%',:searchValue,'%') " )
    List<String> autoSearchByMarketTitle(@Param("searchValue") String searchValue);


    @Query(" SELECT new com.bohan.manalive.web.community.dto.UserMarket.UserMarketManageResponseDto" +
            " (a.seq, a.title, a.isSoldout, a.price, a.createDate, a.modifiedDate," +
            "   ( SELECT COUNT(seq) FROM MarketWish WHERE marketSeq = a.seq), " +
            "   ( SELECT COUNT(seq) FROM MarketInquiry WHERE marketProductSeq = a.seq), " +
            "   ( SELECT url FROM Attach" +
            "      WHERE attNo = ( SELECT MIN(attNo)" +
            "                       FROM Attach " +
            "                       WHERE superKey = a.seq AND category = 'MARKET') )   " +
            " ) " +
            " FROM Market AS a " +
            " WHERE a.email = :email " )
    List<UserMarketManageResponseDto> getUserMarketMangeMarketList(@Param("email") String email);

}
