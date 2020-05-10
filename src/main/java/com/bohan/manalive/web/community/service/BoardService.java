package com.bohan.manalive.web.community.service;

import com.bohan.manalive.config.oauth.LoginUser;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.web.community.domain.Board;
import com.bohan.manalive.web.community.dto.BoardCriteria;
import com.bohan.manalive.web.community.dto.BoardListResponseDto;
import com.bohan.manalive.web.community.dto.BoardSaveRequestDto;

import java.util.HashMap;
import java.util.List;

public interface BoardService {

    public Long saveBoard(BoardSaveRequestDto saveDto, @LoginUser SessionUser user) throws Exception;
    public HashMap<String, Object> boardListandPaging(BoardCriteria criteria);
    public Board boardDetail(Long seq);
}