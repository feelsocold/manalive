package com.bohan.manalive.web.community.domain.UserMarket;

import com.bohan.manalive.web.community.dto.UserMarket.UserMarketResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserMarketRepository extends JpaRepository<UserMarket, Long>,
                                         CrudRepository<UserMarket, Long>{

    List<UserMarket> findByEmail(String email);

    @Query("SELECT seq FROM UserMarket WHERE email = :email")
    Long getUserMarketSeq(@Param("email") String email);

    @Query(" SELECT CONCAT( '@', a.marketName) " +
            " FROM UserMarket AS a " +
            " WHERE a.marketName LIKE CONCAT('%',:searchValue,'%') " )
    List<String> autoSearchByUserMarketName(@Param("searchValue") String searchValue);

    @Query(" SELECT new com.bohan.manalive.web.community.dto.UserMarket.UserMarketResponseDto(a.seq, a.marketName, b.url ) " +
            " FROM UserMarket AS a LEFT OUTER JOIN Attach AS b" +
            "  ON a.seq = b.superKey AND b.category = 'USER_MARKET'" +
            " WHERE a.marketName LIKE CONCAT('%',:searchValue,'%') " )
    List<UserMarketResponseDto> searchUserMarket(@Param("searchValue") String searchValue);

    @Query(" SELECT new com.bohan.manalive.web.community.dto.UserMarket.UserMarketResponseDto(a.seq, a.email, a.marketName, a.introduction, a.visitedGuestCnt, a.createDate, b.url, COUNT(c.seq) ) " +
            " FROM UserMarket AS a " +
            " LEFT OUTER JOIN Attach AS b" +
            "  ON a.seq = b.superKey AND b.category = 'USER_MARKET'" +
            " LEFT OUTER JOIN UserMarketFollow AS c" +
            "  ON a.seq = c.userMarketSeq " +
            " WHERE a.email = :email" +
            " GROUP BY b.url ")
    UserMarketResponseDto getUserMarketInfo(@Param("email") String email);



    @Query(" SELECT new com.bohan.manalive.web.community.dto.UserMarket.UserMarketResponseDto(" +
            " a.followerMarketSeq, c.marketName, " +
            " ( SELECT d.url FROM Attach AS d WHERE d.superKey = a.followerMarketSeq AND d.category = 'USER_MARKET' ), " +
            " ( SELECT COUNT(e.seq) FROM UserMarketFollow AS e WHERE e.userMarketSeq = c.seq), " +
            " ( SELECT COUNT(seq) FROM Market WHERE email = c.email) ) " +
            " FROM UserMarketFollow AS a " +
            "  INNER JOIN UserMarket AS b" +
            "    ON a.userMarketSeq = b.seq " +
            "  LEFT OUTER JOIN UserMarket AS c" +
            "    ON a.followerMarketSeq = c.seq " +
            " WHERE a.userMarketSeq = :userMarketSeq" )
    List<UserMarketResponseDto> getUserMarketFollowerList(@Param("userMarketSeq") Long userMarketSeq);

    @Query(" SELECT new com.bohan.manalive.web.community.dto.UserMarket.UserMarketResponseDto(" +
            " a.userMarketSeq, c.marketName, " +
            " ( SELECT d.url FROM Attach AS d WHERE d.superKey = a.userMarketSeq AND d.category = 'USER_MARKET' ), " +
            " ( SELECT COUNT(e.seq) FROM UserMarketFollow AS e WHERE e.userMarketSeq = c.seq), " +
            " ( SELECT COUNT(seq) FROM Market WHERE email = c.email) ) " +
            " FROM UserMarketFollow AS a " +
            "  INNER JOIN UserMarket AS b" +
            "    ON a.followerMarketSeq = b.seq " +
            "  LEFT OUTER JOIN UserMarket AS c" +
            "    ON a.userMarketSeq = c.seq " +
            " WHERE a.followerMarketSeq = :userMarketSeq" )
    List<UserMarketResponseDto> getUserMarketFollowingList(@Param("userMarketSeq") Long userMarketSeq);


}
