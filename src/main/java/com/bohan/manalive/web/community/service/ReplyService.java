package com.bohan.manalive.web.community.service;

import com.bohan.manalive.web.community.dto.BoardReplyResponseDto;
import com.bohan.manalive.web.community.dto.BoardReplySaveRequestDto;

import java.util.List;
import java.util.Map;

public interface ReplyService {

    public void saveBoardReply(BoardReplySaveRequestDto dto);
    public Map<String, Object> getBoardReplyList(Long b_seq, int pageNumber, int deplayCnt);
    public void deleteBoardReply(Long b_seq);
}
