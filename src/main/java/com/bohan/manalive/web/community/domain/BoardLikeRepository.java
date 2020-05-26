package com.bohan.manalive.web.community.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long>, CrudRepository<BoardLike, Long> {

    @Query(" SELECT count(email) FROM BoardLike" +
            " WHERE b_seq = :b_seq AND email = :email " )
    int discernBoardLike(@Param("b_seq") Long b_seq, @Param("email") String email);

    @Query("SELECT COUNT(a.email) FROM BoardLike a WHERE a.b_seq=:b_seq")
    Long likeCnt(@Param("b_seq") Long b_seq);


    //void deleteByEmailAndB_seq(String email, Long b_seq);
    @Query("DELETE FROM BoardLike WHERE b_seq = :b_seq AND email = :email")
    void unLike(@Param("b_seq") Long b_seq, @Param("email") String email);


}
