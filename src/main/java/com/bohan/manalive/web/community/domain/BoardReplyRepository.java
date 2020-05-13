package com.bohan.manalive.web.community.domain;

import com.bohan.manalive.web.community.dto.BoardReplyResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardReplyRepository extends JpaRepository<BoardReply, Long>,
                                                CrudRepository<BoardReply, Long> {

    @Query(" SELECT new com.bohan.manalive.web.community.dto.BoardReplyResponseDto(" +
            "   a.r_seq, a.content, a.createDate, b.nickname, b.photo, b.email)" +
            " FROM BoardReply a INNER JOIN User b" +
            "   ON a.replyer = b.email" +
            " WHERE a.b_seq = :seq" +
            " ORDER BY a.r_seq DESC ")
    List<BoardReplyResponseDto> getBoardReplyList(@Param("seq") Long seq);


}
