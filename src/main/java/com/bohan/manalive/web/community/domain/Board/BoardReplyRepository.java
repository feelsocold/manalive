package com.bohan.manalive.web.community.domain.Board;

import com.bohan.manalive.web.community.dto.Board.BoardReplyResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface BoardReplyRepository extends JpaRepository<BoardReply, Long>,
                                                CrudRepository<BoardReply, Long> {

    @Query(" SELECT new com.bohan.manalive.web.community.dto.Board.BoardReplyResponseDto(" +
            "   a.r_seq, a.content, a.createDate, b.nickname, b.photo, b.email)" +
            " FROM BoardReply a INNER JOIN User b" +
            "   ON a.replyer = b.email" +
            " WHERE a.b_seq = :seq" +
            " ORDER BY a.r_seq DESC ")
    List<BoardReplyResponseDto> getBoardReplyList(@Param("seq") Long seq);

    @Transactional
    @Modifying
    @Query(" DELETE FROM BoardReply " +
            " WHERE r_seq = :r_seq" )
    Integer deleteByR_seq(@Param("r_seq") Long r_seq);


}
