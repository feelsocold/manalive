package com.bohan.manalive.web.community.domain.Market;

import com.bohan.manalive.web.common.dto.AttachResponseDto;
import com.bohan.manalive.web.community.domain.Board.Board;
import com.bohan.manalive.web.community.dto.Board.BoardResponseDto;
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





}
