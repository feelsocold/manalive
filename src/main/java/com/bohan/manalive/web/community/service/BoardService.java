package com.bohan.manalive.web.community.service;

import com.bohan.manalive.config.oauth.LoginUser;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.web.community.domain.Board;
import com.bohan.manalive.web.community.dto.BoardCriteria;
import com.bohan.manalive.web.community.dto.BoardListResponseDto;
import com.bohan.manalive.web.community.dto.BoardRequestDto;

import java.util.HashMap;
import java.util.List;

public interface BoardService {

    public Long saveBoard(BoardRequestDto saveDto, @LoginUser SessionUser user) throws Exception;
    public HashMap<String, Object> boardListandPaging(BoardCriteria criteria);
    public HashMap<String, Object> boardDetail(Long seq);
    public void deleteBoard(Long seq) throws Exception;
    public List<BoardListResponseDto> getBoardList(BoardCriteria criteria) throws Exception;

}