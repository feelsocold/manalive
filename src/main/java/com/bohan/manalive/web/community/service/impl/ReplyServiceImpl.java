package com.bohan.manalive.web.community.service.impl;

import com.bohan.manalive.web.community.domain.Board.BoardReplyRepository;
import com.bohan.manalive.web.community.dto.Board.BoardReplyResponseDto;
import com.bohan.manalive.web.community.dto.Board.BoardReplySaveRequestDto;
import com.bohan.manalive.web.community.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final BoardReplyRepository replyRepository;

    @Override
    public void saveBoardReply(BoardReplySaveRequestDto requestDto) {
        replyRepository.save(requestDto.toEntity().builder()
                                .b_seq(requestDto.getB_seq())
                                .content(requestDto.getContent())
                                .replyer(requestDto.getReplyer()).build()
        );
    }

    @Override
    public Map<String, Object> getBoardReplyList(Long b_seq, int pageNumber, int delayCnt) {
        List<BoardReplyResponseDto> replyList = replyRepository.getBoardReplyList(b_seq);
        log.info("댓글pagenumber : " + pageNumber);
        int start = (pageNumber * 10) - delayCnt;
        int end = (pageNumber+1) * 10;
        if( replyList.size() - ((pageNumber+1)*10) < 0 || replyList.size() < 10) {
            end = replyList.size();
        };

        Pageable paging = PageRequest.of(pageNumber, 10, Sort.Direction.DESC, "r_seq");
        Page<BoardReplyResponseDto> pageInfo = new PageImpl<>(replyList.subList(start, end), paging, replyList.size());

        List<BoardReplyResponseDto> pagingList = pageInfo.getContent();
        HashMap<String, Object> map = new HashMap<>();

        map.put("replyList", pagingList.size() != 0 ? pagingList : null);
        map.put("replyCnt", replyList.size());

        return map;
    }

    @Override
    public void deleteBoardReply(Long r_seq) {
        log.info("왜 삭제가 안되니?");
        log.info(r_seq + "@");

        Integer cnt =  replyRepository.deleteByR_seq(r_seq);
        log.info(cnt + "@@");

    }
}
