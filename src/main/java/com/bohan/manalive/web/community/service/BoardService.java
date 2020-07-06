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

// 자유게시판
    //등록
    public Long saveBoard(BoardRequestDto saveDto, @LoginUser SessionUser user) throws Exception;
    //삭제
    public void deleteBoard(Long seq) throws Exception;
    //수정
    public Long updateBoard(BoardRequestDto dto) throws Exception;
    //리스트
    public HashMap<String, Object> boardListandPaging(BoardCriteria criteria);
    //상세
    public HashMap<String, Object> boardDetail(Long seq);
    //상세(X)
    public List<Board> getBoardList(BoardCriteria criteria) throws Exception;

    //좋아요
    public void doLikeBoard(BoardLikeRequestDto dto) throws Exception;
    public void doUnLikeBoard(BoardLikeRequestDto dto) throws Exception;
    //좋아요 중복체크
    public boolean discernLikeBoard(BoardLikeRequestDto dto) throws Exception;
    //조회수+
    public void increaseReadcount(Long seq);

}