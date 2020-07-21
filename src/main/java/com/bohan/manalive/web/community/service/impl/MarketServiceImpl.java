package com.bohan.manalive.web.community.service.impl;

import com.bohan.manalive.config.oauth.LoginUser;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.web.common.dto.AttachDto;
import com.bohan.manalive.web.common.service.AttachService;
import com.bohan.manalive.web.community.domain.Market.*;
import com.bohan.manalive.web.community.domain.UserMarket.UserMarket;
import com.bohan.manalive.web.community.domain.UserMarket.UserMarketFollow;
import com.bohan.manalive.web.community.domain.UserMarket.UserMarketFollowRepository;
import com.bohan.manalive.web.community.domain.UserMarket.UserMarketRepository;
import com.bohan.manalive.web.community.dto.Market.*;
import com.bohan.manalive.web.community.dto.UserMarket.UserMarketFollowRequestDto;
import com.bohan.manalive.web.community.dto.UserMarket.UserMarketRequestDto;
import com.bohan.manalive.web.community.dto.UserMarket.UserMarketResponseDto;
import com.bohan.manalive.web.community.service.MarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class MarketServiceImpl implements MarketService {

    private final AttachService attachService;
    private final MarketRepository marketRepo;
    private final MarketWishRepository marketWishRepo;
    private final UserMarketRepository userMarketRepo;
    private final UserMarketFollowRepository userMarketFollowRepo;
    private final MarketInquiryRepository marketInquiryRepo;
    private final MarketInquiryAnswerRepository marketInquiryAnswerRepo;

    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @Override
    public Long saveMarket(MarketRequestDto requestDto) throws Exception {
        return marketRepo.save(requestDto.toEntity()).getSeq();
    }

    @Override
    public HashMap<String, Object> getMarketList(MarketCriteria criteria) throws Exception {
        Pageable paging = null;
        Page<Market> pageInfo = null;
        log.info("페이지넘버 : " + criteria.getPageNumber());
        paging = PageRequest.of(criteria.getPageNumber()-1, criteria.getPageAmount(), Sort.Direction.DESC, criteria.getSorting());

        // default
        if( (criteria.getKeyword() == null || criteria.getKeyword().equals("")) &&
                (criteria.getCategory() == null || criteria.getCategory().equals(""))
        ) {
            log.info("criteria는 없음");
            pageInfo = marketRepo.findAll(paging);
        }
        // 조건을 가질 때
        else if( (criteria.getKeyword() != null && !criteria.getKeyword().equals("") ) ||
                (criteria.getCategory() != null && !criteria.getCategory().equals("")) ){

            Map<String, Object> searchRequest = new HashMap<>();
            searchRequest.put(criteria.getCategory(), criteria.getKeyword());

            Map<MarketSpecs.SearchKey, Object> searchKeys = new HashMap<>();
            for (String key : searchRequest.keySet()) {
                searchKeys.put(MarketSpecs.SearchKey.valueOf(key.toUpperCase()), searchRequest.get(key));
            }
            pageInfo = marketRepo.findAll(MarketSpecs.searchWith(searchKeys), paging);
        }

        Long total = pageInfo.getTotalElements();
        log.info("MARKET 상품 갯수 : " + total);
        List<Market> marketList = pageInfo.getContent();
        //log.info(marketList.size() + " <- 마켓리스트 사이즈");

        List<String> attachList = new ArrayList<>();
        for(Market dto : marketList){
            attachList.add(attachService.getAttachList(dto.getSeq(), "MARKET").get(0).getUrl());
        }

        HashMap<String, Object> map = new HashMap<>();
        //map.put("userDetail", detailList);



        map.put("attachList", attachList);
        map.put("marketList", marketList);
        map.put("pageMaker", new MarketPageDto(criteria, (int)(long)total ));

        return map;
    }

    @Override
    public HashMap<String, Object> getMarketDetail(Long seq, @LoginUser SessionUser user) throws Exception {



        HashMap<String, Object> map = new HashMap<>();
        Market market = marketRepo.findById(seq).get();
        List<AttachDto> attachList = attachService.getAttachList(seq, "MARKET");

        map.put("attachList", attachList);
        map.put("userMarketDto", market.getUserMarket());
        map.put("marketDto", market);
        map.put("inquiryList", getMarketInquiryList(seq, 0, 0) );

        if(user != null){
            map.put("sessionUserMarketSeq", userMarketRepo.getUserMarketSeq(user.getEmail()));
        }
        return map;
    }

    @Override
    public void increaseMarketReadCount(Long seq) throws Exception {
        Market market = marketRepo.findById(seq).get();
        int bfCnt = market.getReadCount();
        marketRepo.findById(seq)
                .map(entity -> entity.updateReadCount(bfCnt+1));
    }

    @Override
    public Long openMarket(UserMarketRequestDto dto) throws Exception {
        return userMarketRepo.save(dto.toEntity()).getSeq();
    }

    @Override
    public boolean saveMarketWish(MarketWishRequestDto dto) throws Exception {
        Long cnt = marketWishRepo.save(dto.toEntity()).getSeq();

        return cnt>0 ? true : false;
    }

    @Override
    public boolean checkDuplicatedWish(MarketWishRequestDto dto) throws Exception {
        List<MarketWish> list = marketWishRepo.findByEmailAndMarketSeq(dto.getEmail(), dto.getMarketSeq());
        log.info("중복 리스트 사이즈: " + list.size());
        return list.size()>0 ? true : false;
    }

    @Override
    public boolean deleteMarketWish(MarketWishRequestDto dto) throws Exception {
        Long cnt = marketWishRepo.deleteByEmailAndMarketSeq(dto.getEmail(), dto.getMarketSeq());
        return cnt>1 ? true : false;
    }

    @Override
    public boolean saveMarketInquiry(MarketInquiryRequestDto dto, SessionUser user) throws Exception {
        List <UserMarket> list = userMarketRepo.findByEmail(user.getEmail());
        dto.setUserMarketSeq(list.get(0).getSeq());
        dto.setCreateDate(currentDateTime);
        Long seq = marketInquiryRepo.save(dto.toEntity()).getSeq();
        return seq>0 ? true : false;
    }

    @Override
    public boolean deleteMarketInquiry(Long inquirySeq) throws Exception {

        marketInquiryRepo.deleteById(inquirySeq);

        return false;
    }

    @Override
    public boolean saveMarketInquiryAnswer(MarketInquiryAnswerRequestDto dto) throws Exception {
        dto.setCreateDate(currentDateTime);
        Long seq = marketInquiryAnswerRepo.save(dto.toEntity()).getSeq();
        return seq>0 ? true : false;
    }

    @Override
    public List<MarketInquiryResponseDto> getMarketInquiryList(Long marketSeq, int pageNumber, int delayCnt) throws Exception {
        List<MarketInquiryResponseDto> list = marketInquiryRepo.getMarketInquiryList(marketSeq);
        int mount = 5;
        int start = (pageNumber * mount)-delayCnt;
        int end = (pageNumber+1) * mount;
        if( list.size() - ((pageNumber+1)*mount) < 0 || list.size() < mount) {
            end = list.size();
        };
        Pageable paging = PageRequest.of(pageNumber, mount, Sort.Direction.DESC, "seq");
        Page<MarketInquiryResponseDto> pageInfo = new PageImpl<>(list.subList(start, end), paging, list.size());
        List<MarketInquiryResponseDto> pagingList = pageInfo.getContent();
        return pagingList;
    }


    @Override
    public List<String> autoSearchMarket(String searchValue) throws Exception {
        if(searchValue.substring(0, 1).equals("@")){
            log.info("상점 이름 검색");
            searchValue = searchValue.substring(1);
            return userMarketRepo.autoSearchByUserMarketName(searchValue);
        }else{
            log.info("상점 상품 검색");
            return marketRepo.autoSearchByMarketTitle(searchValue);
        }
    }

    @Override
    public HashMap<String, Object> searchUserMarketName(String searchValue, int pageNumber) throws Exception {
        List<UserMarketResponseDto> list = userMarketRepo.searchUserMarket(searchValue);
        log.info("USERMARKET pagenumber : " + pageNumber);
        int start = (pageNumber * 20);
        int end = (pageNumber+1) * 20;
        if( list.size() - ((pageNumber+1)*20) < 0 || list.size() < 20) {
            end = list.size();
        };
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.Direction.DESC, "seq");
        Page<UserMarketResponseDto> pageInfo = new PageImpl<>(list.subList(start, end), paging, list.size());
        List<UserMarketResponseDto> pagingList = pageInfo.getContent();
        //return userMarketRepo.searchUserMarket(searchValue);
        HashMap<String, Object> map = new HashMap<>();
        map.put("userMarketList", pagingList.size() != 0 ? pagingList : null);
        map.put("userMarketCnt", list.size());

        return map;
    }
    @Transactional
    @Override
    public HashMap<String, Object> getUserMarket(Long seq) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        log.info("여기여기여기여기여기여기여기여기여기여기여기여기");
        String email = userMarketRepo.findById(seq).get().getEmail();
        log.info(email + "@@@@@@@@@@@@@@@@@");
        MarketCriteria criteria = new MarketCriteria();
        criteria.setCategory("EMAIL");
        criteria.setKeyword(email);
        map = getMarketList(criteria);
        map.put("userMarketInfo", userMarketRepo.getUserMarketInfo(email));
        return map;
    }

    @Override
    public boolean checkDuplicatedFollow(UserMarketFollowRequestDto dto) throws Exception {
//        List<UserMarketFollow> list = userMarketFollowRepo.findByEmailAndUserMarketSeq(dto.getEmail(), dto.getUserMarketSeq());
        Long followerMarketSeq = userMarketRepo.getUserMarketSeq(dto.getEmail());
        List<UserMarketFollow> list = userMarketFollowRepo.findByUserMarketSeqAndAndFollowerMarketSeq(dto.getUserMarketSeq(), followerMarketSeq);
        return list.size()>0 ? true : false;
    }

    @Override
    public boolean followUserMarket(UserMarketFollowRequestDto dto) throws Exception {
        Long followerMarketSeq = userMarketRepo.getUserMarketSeq(dto.getEmail());
        dto.setFollowerMarketSeq(followerMarketSeq);
        Long seq = userMarketFollowRepo.save(dto.toEntity()).getSeq();
        return seq!=0 ? true:false;
    }

    @Override
    public boolean unfollowUserMarket(UserMarketFollowRequestDto dto) throws Exception {
        Long followerMarketSeq = userMarketRepo.getUserMarketSeq(dto.getEmail());
        dto.setFollowerMarketSeq(followerMarketSeq);
        Long cnt = userMarketFollowRepo.deleteByUserMarketSeqAndFollowerMarketSeq(dto.getUserMarketSeq(),followerMarketSeq );
        log.info("DELETE CNT : " + cnt);
        return cnt>0 ? false : true;
    }

    @Override
    public HashMap<String, Object> getUserMarketFollowerList(Long userMarketSeq, int pageNumber) throws Exception {
        List<UserMarketResponseDto> list = userMarketRepo.getUserMarketFollowerList(userMarketSeq);
        int mount = 10;
        int start = (pageNumber * mount);
        int end = (pageNumber+1) * mount;
        if( list.size() - ((pageNumber+1)*mount) < 0 || list.size() < mount) {
            end = list.size();
        };
        Pageable paging = PageRequest.of(pageNumber, mount, Sort.Direction.DESC, "seq");
        Page<UserMarketResponseDto> pageInfo = new PageImpl<>(list.subList(start, end), paging, list.size());
        List<UserMarketResponseDto> pagingList = pageInfo.getContent();
        HashMap<String, Object> map = new HashMap<>();

        map.put("followerList", pagingList.size() != 0 ? pagingList : null);
        map.put("followTotal", list.size());

        return map;
    }

    @Override
    public HashMap<String, Object> getUserMarketFollowingList(Long userMarketSeq, int pageNumber) throws Exception {
        List<UserMarketResponseDto> list = userMarketRepo.getUserMarketFollowingList(userMarketSeq);
        int mount = 10;
        int start = (pageNumber * mount);
        int end = (pageNumber+1) * mount;
        if( list.size() - ((pageNumber+1)*mount) < 0 || list.size() < mount) {
            end = list.size();
        };
        Pageable paging = PageRequest.of(pageNumber, mount, Sort.Direction.DESC, "seq");
        Page<UserMarketResponseDto> pageInfo = new PageImpl<>(list.subList(start, end), paging, list.size());
        List<UserMarketResponseDto> pagingList = pageInfo.getContent();
        HashMap<String, Object> map = new HashMap<>();

        map.put("followingList", pagingList.size() != 0 ? pagingList : null);
        map.put("followTotal", list.size());

        return map;
    }


}
