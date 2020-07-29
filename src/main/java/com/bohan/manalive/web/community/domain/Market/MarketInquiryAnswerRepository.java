package com.bohan.manalive.web.community.domain.Market;

import com.bohan.manalive.web.community.dto.Market.MarketInquiryResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MarketInquiryAnswerRepository extends JpaRepository<MarketInquiryAnswer, Long>,
                                         CrudRepository<MarketInquiryAnswer, Long> {

//    @Query(" SELECT new com.bohan.manalive.web.community.dto.Market.MarketInquiryResponseDto(" +
//            " a.seq, a.content, a.userMarketSeq, a.createDate, a.modifiedDate,  c.marketName, " +
//            " ( SELECT url FROM Attach WHERE superKey = a.userMarketSeq AND category = 'USER_MARKET' ) ) " +
//            " FROM MarketInquiry AS a " +
//            "  INNER JOIN Market AS b" +
//            "    ON a.marketProductSeq = b.seq " +
//            "  INNER JOIN UserMarket AS c" +
//            "    ON a.userMarketSeq = c.seq " +
//            " WHERE a.marketProductSeq = :marketProductSeq" +
//            " ORDER BY a.seq DESC " )
//    List<MarketInquiryResponseDto> getMarketInquiryList(@Param("marketProductSeq") Long marketProductSeq);
    List<MarketInquiryAnswer> findByMarketInquirySeq(Long marketInquirySeq);



}
