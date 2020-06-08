package com.bohan.manalive.web.community.service.impl;

import com.bohan.manalive.web.community.domain.Market.Market;
import com.bohan.manalive.web.community.domain.Market.MarketRepository;
import com.bohan.manalive.web.community.domain.Market.MarketSpecs;
import com.bohan.manalive.web.community.dto.Market.MarketCriteria;
import com.bohan.manalive.web.community.dto.Market.MarketPageDto;
import com.bohan.manalive.web.community.dto.Market.MarketRequestDto;
import com.bohan.manalive.web.community.dto.Board.PageDto;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class MarketServiceImpl implements MarketService {


    private final MarketRepository marketRep;

    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @Override
    public Long saveBoard(MarketRequestDto requestDto) throws Exception {
        return marketRep.save(requestDto.toEntity()).getSeq();
    }

    @Override
    public HashMap<String, Object> getMarketList(MarketCriteria criteria) {
        Pageable paging = null;
        Page<Market> pageInfo = null;
        log.info("페이지넘버 : " + criteria.getPageNumber() + "!!!!!");
        paging = PageRequest.of(criteria.getPageNumber()-1, criteria.getPageAmount(), Sort.Direction.DESC, criteria.getSorting());

        // default
        if( (criteria.getKeyword() == null || criteria.getKeyword().equals("")) &&
                (criteria.getCategory() == null || criteria.getCategory().equals(""))
        ) {
            log.info("criteria는 없다");

            pageInfo = marketRep.findAll(paging);
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
            pageInfo = marketRep.findAll(MarketSpecs.searchWith(searchKeys), paging);
        }

        Long total = pageInfo.getTotalElements();
        log.info("board total : " + total);
        List<Market> marketList = pageInfo.getContent();
        log.info(marketList.size() + " <- 마켓리스트 사이즈");

//        List<BoardUserDetailDto> detailList = new ArrayList<>();
//        for(Market BoardDto : boardList){
//            BoardUserDetailDto userDetail = new BoardUserDetailDto();
//            userDetail.setNickname(BoardDto.getUserDetail().getNickname());
//            userDetail.setPhoto(BoardDto.getUserDetail().getPhoto());
//            detailList.add(userDetail);
//        }

        HashMap<String, Object> map = new HashMap<>();
        //map.put("userDetail", detailList);
        map.put("marketList", marketList);
        map.put("pageMaker", new MarketPageDto(criteria, (int)(long)total ));

        return map;
    }
}
