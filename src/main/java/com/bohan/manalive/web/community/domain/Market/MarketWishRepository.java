package com.bohan.manalive.web.community.domain.Market;

import com.bohan.manalive.web.community.dto.Market.MarketWishResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Converter;
import java.util.List;

@EnableJpaRepositories(basePackageClasses = {MarketWish.class})
@Repository
public interface MarketWishRepository extends JpaRepository<MarketWish, Long>,
                                         CrudRepository<MarketWish, Long>{

    List<MarketWish> findByUserMarketSeqAndMarketSeq(Long userMarketSeq, Long marketSeq);
    Long deleteByUserMarketSeqAndMarketSeq(Long userMarketSeq, Long marketSeq);


    @Query(" SELECT new com.bohan.manalive.web.community.dto.Market.MarketWishResponseDto(" +
            " a.seq, a.marketSeq, b.title, a.createDate, b.isSoldout,   " +
            "   ( SELECT url " +
            "       FROM Attach " +
            "       WHERE attNo = ( SELECT MIN(attNo)" +
            "                       FROM Attach " +
            "                       WHERE superKey = a.marketSeq AND category = 'MARKET') ))   " +
            " FROM MarketWish AS a " +
            "  INNER JOIN Market AS b" +
            "    ON a.marketSeq = b.seq " +
            " WHERE a.userMarketSeq = :userMarketSeq" +
            " ORDER BY a.seq DESC ")
    List<MarketWishResponseDto> getMarketWishList(@Param("userMarketSeq") Long userMarketSeq);

}
