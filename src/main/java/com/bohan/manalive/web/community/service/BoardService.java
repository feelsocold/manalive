package com.bohan.manalive.web.community.service;

import com.bohan.manalive.config.oauth.LoginUser;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.web.community.domain.Board.Board;
import com.bohan.manalive.web.community.dto.Board.BoardCriteria;
import com.bohan.manalive.web.community.dto.Board.BoardLikeRequestDto;
import com.bohan.manalive.web.community.dto.Board.BoardRequestDto;

import java.util.HashMap;
import java.util.List;

public interface BoardService {

    public Long saveBoard(BoardRequestDto saveDto, @LoginUser SessionUser user) throws Exception;
    public void deleteBoard(Long seq) throws Exception;
    public Long updateBoard(BoardRequestDto dto) throws Exception;
    public HashMap<String, Object> boardListandPaging(BoardCriteria criteria);
    public HashMap<String, Object> boardDetail(Long seq);
    public List<Board> getBoardList(BoardCriteria criteria) throws Exception;
    public void doLikeBoard(BoardLikeRequestDto dto) throws Exception;
    public void doUnLikeBoard(BoardLikeRequestDto dto) throws Exception;
    public boolean discernLikeBoard(BoardLikeRequestDto dto) throws Exception;
    public void increaseReadcount(Long seq);

}