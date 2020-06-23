package com.bohan.manalive.web.community.service.impl;

import com.bohan.manalive.web.common.domain.attach.Attach;
import com.bohan.manalive.web.common.dto.AttachDto;
import com.bohan.manalive.web.common.dto.AttachResponseDto;
import com.bohan.manalive.web.common.service.AttachService;
import com.bohan.manalive.web.community.domain.Market.Market;
import com.bohan.manalive.web.community.domain.Market.MarketRepository;
import com.bohan.manalive.web.community.domain.Market.MarketSpecs;
import com.bohan.manalive.web.community.domain.UserMarket.UserMarketRepository;
import com.bohan.manalive.web.community.dto.Market.MarketCriteria;
import com.bohan.manalive.web.community.dto.Market.MarketPageDto;
import com.bohan.manalive.web.community.dto.Market.MarketRequestDto;
import com.bohan.manalive.web.community.dto.Board.PageDto;
import com.bohan.manalive.web.community.dto.Market.MarketResponseDto;
import com.bohan.manalive.web.community.dto.UserMarket.UserMarketRequestDto;
import com.bohan.manalive.web.community.service.MarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MarketServiceImpl implements MarketService {

    private final MarketRepository marketRepo;
    private final UserMarketRepository userMarketRepo;
    private final AttachService attachService;

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
        log.info("페이지넘버 : " + criteria.getPageNumber() + "!!!!!");
        paging = PageRequest.of(criteria.getPageNumber()-1, criteria.getPageAmount(), Sort.Direction.DESC, criteria.getSorting());

        // default
        if( (criteria.getKeyword() == null || criteria.getKeyword().equals("")) &&
                (criteria.getCategory() == null || criteria.getCategory().equals(""))
        ) {
            log.info("criteria는 없다");

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
        log.info("MARKET total : " + total);
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
    public HashMap<String, Object> getMarketDetail(Long seq) throws Exception {

        HashMap<String, Object> map = new HashMap<>();
        Market market = marketRepo.findById(seq).get();

        List<AttachDto> attachList = attachService.getAttachList(seq, "MARKET");

        map.put("attachList", attachList);
        map.put("userMarketDto", market.getUserMarket());
        map.put("marketDto", market);

        return map;
    }

    @Override
    public Long openMarket(UserMarketRequestDto dto) throws Exception {
        return userMarketRepo.save(dto.toEntity()).getSeq();
    }


}
