package com.bohan.manalive.web.community.service.impl;

import com.bohan.manalive.config.oauth.LoginUser;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.web.common.dto.AttachResponseDto;
import com.bohan.manalive.web.community.domain.Board;
import com.bohan.manalive.web.community.domain.BoardRepository;
import com.bohan.manalive.web.community.domain.BoardSpecs;
import com.bohan.manalive.web.community.dto.*;
import com.bohan.manalive.web.community.service.BoardService;
import com.bohan.manalive.web.community.service.ReplyService;
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

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepo;
    private final ReplyService replyService;

    @Override
    public Long saveBoard(BoardRequestDto requestDto, @LoginUser SessionUser user) throws Exception {
        return boardRepo.save(requestDto.toEntity()).getSeq();
    }

    @Transactional
    @Override
    public HashMap<String, Object> boardListandPaging(BoardCriteria criteria) {
        Pageable paging = null;
        Page<Board> pageInfo = null;

        paging = PageRequest.of(criteria.getPageNumber()-1, criteria.getPageAmount(), Sort.Direction.DESC, criteria.getSorting());

        // default
        if(criteria.getKeyword() == null || criteria.getKeyword().equals("")){
            log.info("criteria는 없다");

            pageInfo = boardRepo.findAll(paging);
        }
        // 조건을 가질 때
        else{
            Map<String, Object> searchRequest = new HashMap<>();
            searchRequest.put(criteria.getCategory(), criteria.getKeyword());

            Map<BoardSpecs.SearchKey, Object> searchKeys = new HashMap<>();
            for (String key : searchRequest.keySet()) {
                searchKeys.put(BoardSpecs.SearchKey.valueOf(key.toUpperCase()), searchRequest.get(key));
            }
            pageInfo = boardRepo.findAll(BoardSpecs.searchWith(searchKeys), paging);

        }

        Long total = pageInfo.getTotalElements();
        log.info("board total : " + total);
        List<Board> boardList = pageInfo.getContent();
        HashMap<String, Object> map = new HashMap<>();
        map.put("boardList", boardList);
        map.put("pageMaker", new PageDto(criteria, (int)(long)total ));

        return map;
    }

    @Override
    public HashMap<String, Object> boardDetail(Long seq) {
        HashMap<String, Object> map = new HashMap<>();
        Map<String, Object> replyObj = new HashMap<>();

        BoardResponseDto boardDto = boardRepo.getBoardDetail(seq);
        map.put("boardDto", boardDto);

        List<AttachResponseDto> attachList = boardRepo.getBoardAttachList(seq);
        map.put("attachList", attachList);

        //댓글 리스트
        //List<BoardReplyResponseDto> replyList = replyService.getBoardReplyList(seq, 0);
        replyObj = replyService.getBoardReplyList(seq, 0);
        map.put("replyObj", replyObj);

        return map;
    }


}
