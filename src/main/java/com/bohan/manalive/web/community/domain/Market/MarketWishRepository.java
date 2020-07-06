package com.bohan.manalive.web.community.domain.Market;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories(basePackageClasses = {MarketWish.class})
@Repository
public interface MarketWishRepository extends JpaRepository<MarketWish, Long>,
                                         CrudRepository<MarketWish, Long>{

    List<MarketWish> findByEmailAndMarketSeq(String email, Long marketSeq);
    Long deleteByEmailAndMarketSeq(String email, Long marketSeq);

}
