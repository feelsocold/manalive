package com.bohan.manalive.web.community.domain.UserMarket;

import com.bohan.manalive.web.community.domain.Market.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface UserMarketRepository extends JpaRepository<UserMarket, Long>,
                                         CrudRepository<UserMarket, Long>{





}
