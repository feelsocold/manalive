package com.bohan.manalive.web.community.service;

import com.bohan.manalive.config.oauth.LoginUser;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.web.community.domain.Board.Board;
import com.bohan.manalive.web.community.dto.Board.BoardCriteria;
import com.bohan.manalive.web.community.dto.Board.BoardLikeRequestDto;
import com.bohan.manalive.web.community.dto.Board.BoardRequestDto;
import com.bohan.manalive.web.community.dto.Market.MarketCriteria;
import com.bohan.manalive.web.community.dto.Market.MarketRequestDto;
import com.bohan.manalive.web.community.dto.Market.MarketWishRequestDto;
import com.bohan.manalive.web.community.dto.UserMarket.UserMarketRequestDto;
import com.bohan.manalive.web.community.dto.UserMarket.UserMarketResponseDto;

import java.util.HashMap;
import java.util.List;

public interface MarketService {


    // 중고마켓
    public Long saveMarket(MarketRequestDto dto) throws Exception;
    public HashMap<String, Object> getMarketList(MarketCriteria criteria) throws Exception;
    public HashMap<String, Object> getMarketDetail(Long seq) throws Exception;

    // 중고마켓 조회
    public void increaseMarketReadCount(Long seq) throws Exception;

    // 중고마켓 만들기
    public Long openMarket(UserMarketRequestDto dto) throws Exception;

    // 중고마켓 찜하기
    public boolean saveMarketWish(MarketWishRequestDto dto)throws Exception;
    // 중고마켓 찜 중복 확인
    public boolean checkDuplicatedWish(MarketWishRequestDto dto) throws Exception;
    // 중고마켓 찜해제
    public boolean deleteMarketWish(MarketWishRequestDto dto) throws Exception;

    // 중고마켓 검색 자동완성
    public List<String> autoSearchMarket(String searchValue) throws Exception;

    // 중고마켓 회원마켓 검색시 회원마켓 리스트 출력(페이징10개)
    public HashMap<String, Object> searchUserMarketName(String searchValue, int pageNumber) throws Exception;

    // 중고마켓 상점관리
    public HashMap<String, Object> getUserMarketMangeList(MarketCriteria criteria) throws Exception;
}