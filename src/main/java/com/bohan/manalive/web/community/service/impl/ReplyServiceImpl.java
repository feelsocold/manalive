package com.bohan.manalive.web.community.service.impl;

import com.bohan.manalive.web.community.domain.BoardReplyRepository;
import com.bohan.manalive.web.community.dto.BoardReplyResponseDto;
import com.bohan.manalive.web.community.dto.BoardReplySaveRequestDto;
import com.bohan.manalive.web.community.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, Object> getBoardReplyList(Long b_seq, int PageNumber) {
        List<BoardReplyResponseDto> replyList = replyRepository.getBoardReplyList(b_seq);
        //return replyRepository.getBoardReplyList(b_seq);
        int end = (replyList.size() >= 10) ? 10 : replyList.size();
        Pageable paging = PageRequest.of(PageNumber, 10, Sort.Direction.DESC, "r_seq");
        Page<BoardReplyResponseDto> pageInfo = new PageImpl<>(replyList.subList(0, end), paging, replyList.size());

        System.out.println("댓글리스트 사이즈 : " + pageInfo.getContent().size());

        List<BoardReplyResponseDto> pagingList = pageInfo.getContent();
        HashMap<String, Object> map = new HashMap<>();

        map.put("replyList", pagingList.size() != 0 ? pagingList : null);
        map.put("replyCnt", replyList.size());

        return map;
    }
}
