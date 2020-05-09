package com.bohan.manalive.web.community.service.impl;

import com.bohan.manalive.config.oauth.LoginUser;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.web.community.domain.Board;
import com.bohan.manalive.web.community.domain.BoardRepository;
import com.bohan.manalive.web.community.domain.BoardSpecs;
import com.bohan.manalive.web.community.dto.BoardCriteria;
import com.bohan.manalive.web.community.dto.BoardListResponseDto;
import com.bohan.manalive.web.community.dto.BoardSaveRequestDto;
import com.bohan.manalive.web.community.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepo;

//    @Transactional
//    @Override
//    public List<BoardListResponseDto> findAllDesc() throws Exception {
//        return boardRepo.findAllDesc().stream()
//                .map(BoardListResponseDto::new)
//                .collect(Collectors.toList());
//    }

    @Override
    public Long saveBoard(BoardSaveRequestDto requestDto, @LoginUser SessionUser user) throws Exception {
        return boardRepo.save(requestDto.toEntity()).getSeq();
    }

    @Transactional
    @Override
    public List<Board> boardList(BoardCriteria criteria) {
        Pageable paging = null;
        Page<Board> pageInfo = null;

        if(criteria.getKeyword() == null || criteria.getKeyword().equals("")){
            paging = PageRequest.of(0, 15, Sort.Direction.DESC, "seq");
            pageInfo = boardRepo.findAll(paging);
        }else{

            log.info("여기있다!!!!!!!!!1");


            Map<String, Object> searchRequest = new HashMap<>();
            searchRequest.put(criteria.getCategory(), criteria.getKeyword());

            Map<BoardSpecs.SearchKey, Object> searchKeys = new HashMap<>();
            for (String key : searchRequest.keySet()) {
                searchKeys.put(BoardSpecs.SearchKey.valueOf(key.toUpperCase()), searchRequest.get(key));
            }

            paging = PageRequest.of(criteria.getPageNumber(), criteria.getPageAmount(), Sort.Direction.DESC, "seq");
            pageInfo = boardRepo.findAll(BoardSpecs.searchWith(searchKeys), paging);
        }
//        Page<Board> pageInfo = boardRepo.findByTitleContaining("", paging);

        List<Board> boardList = pageInfo.getContent();
        log.info(boardList.size() + "");
        return boardList;
    }


}
