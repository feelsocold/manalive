package com.bohan.manalive.web.community.domain.UserMarket;

import com.bohan.manalive.web.community.domain.Market.Market;
import com.bohan.manalive.web.community.dto.Board.BoardResponseDto;
import com.bohan.manalive.web.community.dto.UserMarket.UserMarketResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserMarketRepository extends JpaRepository<UserMarket, Long>,
                                         CrudRepository<UserMarket, Long>{

    @Query(" SELECT CONCAT( '@', a.marketName) " +
            " FROM UserMarket AS a " +
            " WHERE a.marketName LIKE CONCAT('%',:searchValue,'%') " )
    List<String> autoSearchByUserMarketName(@Param("searchValue") String searchValue);

    @Query(" SELECT new com.bohan.manalive.web.community.dto.UserMarket.UserMarketResponseDto(a.seq, a.marketName, b.url ) " +
            " FROM UserMarket AS a LEFT OUTER JOIN Attach AS b" +
            "  ON a.seq = b.superKey AND b.category = 'USER_MARKET'" +
            " WHERE a.marketName LIKE CONCAT('%',:searchValue,'%') " )
    List<UserMarketResponseDto> searchUserMarket(@Param("searchValue") String searchValue);

    @Query(" SELECT new com.bohan.manalive.web.community.dto.UserMarket.UserMarketResponseDto(a.seq, a.marketName, b.url ) " +
            " FROM UserMarket AS a LEFT OUTER JOIN Attach AS b" +
            "  ON a.seq = b.superKey AND b.category = 'USER_MARKET'" +
            " WHERE a.email = :email" )
    UserMarketResponseDto getUserMarkerInfo(@Param("email") String email);


}
