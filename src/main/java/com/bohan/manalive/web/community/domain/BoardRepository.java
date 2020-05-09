package com.bohan.manalive.web.community.domain;

import com.bohan.manalive.web.community.dto.BoardListResponseDto;
import com.bohan.manalive.web.community.dto.BoardSaveRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>,
                                         CrudRepository<Board, Long>,
                                         JpaSpecificationExecutor<Board> {

    @Query("SELECT b FROM Board b ORDER BY b.seq DESC")
    List<Board> findAllDesc();

    Page<Board> findByTitleContaining(String keyword, Pageable paging);




}
