package com.bohan.manalive.web.community.service;

import com.bohan.manalive.config.oauth.LoginUser;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.web.community.domain.Board.Board;
import com.bohan.manalive.web.community.dto.Board.BoardCriteria;
import com.bohan.manalive.web.community.dto.Board.BoardLikeRequestDto;
import com.bohan.manalive.web.community.dto.Board.BoardRequestDto;
import com.bohan.manalive.web.community.dto.Market.MarketCriteria;
import com.bohan.manalive.web.community.dto.Market.MarketRequestDto;
import com.bohan.manalive.web.community.dto.UserMarket.UserMarketRequestDto;

import java.util.HashMap;
import java.util.List;

public interface MarketService {

    public Long saveMarket(MarketRequestDto dto) throws Exception;
    public HashMap<String, Object> getMarketList(MarketCriteria criteria) throws Exception;
    public HashMap<String, Object> getMarketDetail(Long seq) throws Exception;

    public Long openMarket(UserMarketRequestDto dto) throws Exception;


}