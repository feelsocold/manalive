package com.bohan.manalive.web.community.service;

import com.bohan.manalive.web.community.dto.Board.BoardReplySaveRequestDto;

import java.util.Map;

public interface ReplyService {
//자유게시판 댓글
    //등록
    public void saveBoardReply(BoardReplySaveRequestDto dto);
    //리스트
    public Map<String, Object> getBoardReplyList(Long b_seq, int pageNumber, int deplayCnt);
    //삭제
    public void deleteBoardReply(Long b_seq);
}
