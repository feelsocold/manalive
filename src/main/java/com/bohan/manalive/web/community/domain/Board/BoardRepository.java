package com.bohan.manalive.web.community.domain.Board;

import com.bohan.manalive.web.common.dto.AttachResponseDto;
import com.bohan.manalive.web.community.dto.Board.BoardResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>,
                                         CrudRepository<Board, Long>,
                                         JpaSpecificationExecutor<Board>,
                                         QuerydslPredicateExecutor<Board> {

     @Query(" SELECT new com.bohan.manalive.web.community.dto.Board.BoardResponseDto(a.seq, a.title, a.content, a.hashtags, a.readCount, a.createDate, a.modifiedDate, b.nickname, b.photo, b.email ) " +
            " FROM Board AS a INNER JOIN User AS b " +
            "   ON a.email = b.email" +
            " WHERE a.seq = :seq " )
     BoardResponseDto getBoardDetail(@Param("seq") Long seq);

     @Query( " SELECT new com.bohan.manalive.web.common.dto.AttachResponseDto(b.att_no, b.url)" +
             " FROM Board AS a INNER JOIN Attach b " +
             "  ON a.seq = b.superKey AND b.category = 'BOARD'" +
             " WHERE a.seq = :seq ")
     List<AttachResponseDto> getBoardAttachList(@Param("seq") Long seq);


     @Modifying
     @Query( " DELETE FROM Board " +
             " WHERE seq = :seq")
     void deleteBoard(@Param("seq") Long seq);



}
