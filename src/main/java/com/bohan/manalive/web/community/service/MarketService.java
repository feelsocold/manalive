package com.bohan.manalive.web.community.service;

import com.bohan.manalive.config.oauth.LoginUser;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.web.community.domain.Board.Board;
import com.bohan.manalive.web.community.domain.Market.MarketInquiryAnswer;
import com.bohan.manalive.web.community.domain.Market.MarketWish;
import com.bohan.manalive.web.community.dto.Board.BoardCriteria;
import com.bohan.manalive.web.community.dto.Board.BoardLikeRequestDto;
import com.bohan.manalive.web.community.dto.Board.BoardRequestDto;
import com.bohan.manalive.web.community.dto.Market.*;
import com.bohan.manalive.web.community.dto.UserMarket.UserMarketFollowRequestDto;
import com.bohan.manalive.web.community.dto.UserMarket.UserMarketRequestDto;
import com.bohan.manalive.web.community.dto.UserMarket.UserMarketResponseDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MarketService {

    // 중고마켓 판매등록
    public Long saveMarket(MarketRequestDto dto) throws Exception;
    // 중고마켓 메인 리스트
    public HashMap<String, Object> getMarketList(MarketCriteria criteria) throws Exception;
    // 중고마켓 상품 디테일
    public HashMap<String, Object> getMarketDetail(Long seq, @LoginUser SessionUser user) throws Exception;
    // 중고마켓 디테일 조회수++
    public void increaseMarketReadCount(Long seq) throws Exception;


// 유저상점
    // 중고마켓 유저상점 만들기
    public Long openMarket(UserMarketRequestDto dto) throws Exception;
    // 중고마켓 유저상점 메인페이지
    public HashMap<String, Object> getUserMarket(Long seq, @LoginUser SessionUser user) throws Exception;
    // 중고마켓 유저상점 내상점관리 페이지
    public HashMap<String, Object> getUserMarketManage(@LoginUser SessionUser user, int pageNumber) throws Exception;

// 종고마켓 찜
    // 중고마켓 찜하기
    public boolean saveMarketWish(MarketWishRequestDto dto)throws Exception;
    // 중고마켓 찜 중복 확인
    public boolean checkDuplicatedWish(MarketWishRequestDto dto) throws Exception;
    // 중고마켓 찜 해제
    public boolean deleteMarketWish(MarketWishRequestDto dto) throws Exception;
    // 중고마켓 찜 리스트
    public List<MarketWishResponseDto> getUserMarketWishList(Long userMarketSeq) throws Exception;

// 중고마켓 문의
    // 중고마켓 문의 리스트 불러오기
    public HashMap<String, Object> getMarketInquiryList(Long marketSeq, int pageNumber, int delayCnt) throws Exception;
    // 중고마켓 문의 저장
    public boolean saveMarketInquiry(MarketInquiryRequestDto dto, @LoginUser SessionUser user) throws Exception;
    // 중고마켓 문의 삭제
    public boolean deleteMarketInquiry(Long inquirySeq) throws Exception;
    // 중고마켓 문의 답변 저장
    public MarketInquiryAnswer saveMarketInquiryAnswer(MarketInquiryAnswerRequestDto dto) throws Exception;
    // 중고마켓 문의 답변 불러오기
    public List<MarketInquiryAnswer> getMarketInquiryAnswerList(Long marketInquirySeq) throws Exception;


// 중고마켓 검색
    // 중고마켓 검색 자동완성
    public List<String> autoSearchMarket(String searchValue) throws Exception;
    // 중고마켓 회원마켓 검색시 회원마켓 리스트 출력(페이징10개)
    public HashMap<String, Object> searchUserMarketName(String searchValue, int pageNumber) throws Exception;


// 중고마켓 유저상점 팔로우
    // 중고마켓 팔로우 중복 확인
    public boolean checkDuplicatedFollow(UserMarketFollowRequestDto dto) throws Exception;
    // 중고마켓 유저상점 팔로우
    public boolean followUserMarket(UserMarketFollowRequestDto dto) throws Exception;
    // 중고마켓 유저상점 언팔로우
    public boolean unfollowUserMarket(UserMarketFollowRequestDto dto) throws Exception;

    // 유저상점 팔로우 리스트
    public HashMap<String, Object> getUserMarketFollowerList(Long userMarketSeq, int pageNumber) throws Exception;
    // 유점상점 팔로잉 리스트
    public HashMap<String, Object> getUserMarketFollowingList(Long userMarketSeq, int pageNumber) throws Exception;

}