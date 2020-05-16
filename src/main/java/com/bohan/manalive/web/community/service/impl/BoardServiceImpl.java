package com.bohan.manalive.web.community.service.impl;

import com.bohan.manalive.config.oauth.LoginUser;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.domain.user.QUser;
import com.bohan.manalive.domain.user.UserRepository;
import com.bohan.manalive.web.common.dto.AttachResponseDto;
import com.bohan.manalive.web.community.domain.Board;
import com.bohan.manalive.web.community.domain.BoardRepository;
import com.bohan.manalive.web.community.domain.BoardSpecs;
import com.bohan.manalive.web.community.domain.QBoard;
import com.bohan.manalive.web.community.dto.*;
import com.bohan.manalive.web.community.service.AttachService;
import com.bohan.manalive.web.community.service.BoardService;
import com.bohan.manalive.web.community.service.ReplyService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final JPAQueryFactory queryFactory;
    private final UserRepository userRepository;
    private final BoardRepository boardRepo;
    private final ReplyService replyService;
    private final AttachService attachService;
//    @PersistenceContext
//    private EntityManager em;


    @Override
    public Long saveBoard(BoardRequestDto requestDto, @LoginUser SessionUser user) throws Exception {
        return boardRepo.save(requestDto.toEntity()).getSeq();
    }

    @Transactional
    @Override
    public HashMap<String, Object> boardListandPaging(BoardCriteria criteria) {
        Pageable paging = null;
        Page<Board> pageInfo = null;

        paging = PageRequest.of(criteria.getPageNumber()-1, criteria.getPageAmount(), Sort.Direction.DESC, criteria.getSorting());

        // default
        if(criteria.getKeyword() == null || criteria.getKeyword().equals("")){
            log.info("criteria는 없다");

            pageInfo = boardRepo.findAll(paging);
        }
        // 조건을 가질 때
        else{
            Map<String, Object> searchRequest = new HashMap<>();
            searchRequest.put(criteria.getCategory(), criteria.getKeyword());

            Map<BoardSpecs.SearchKey, Object> searchKeys = new HashMap<>();
            for (String key : searchRequest.keySet()) {
                searchKeys.put(BoardSpecs.SearchKey.valueOf(key.toUpperCase()), searchRequest.get(key));
            }
            pageInfo = boardRepo.findAll(BoardSpecs.searchWith(searchKeys), paging);

        }

        Long total = pageInfo.getTotalElements();
        log.info("board total : " + total);
        List<Board> boardList = pageInfo.getContent();
        HashMap<String, Object> map = new HashMap<>();
        map.put("boardList", boardList);
        map.put("pageMaker", new PageDto(criteria, (int)(long)total ));

        return map;
    }

    @Override
    public HashMap<String, Object> boardDetail(Long seq) {
        HashMap<String, Object> map = new HashMap<>();
        Map<String, Object> replyObj = new HashMap<>();

        BoardResponseDto boardDto = boardRepo.getBoardDetail(seq);
        map.put("boardDto", boardDto);

        List<AttachResponseDto> attachList = boardRepo.getBoardAttachList(seq);
        map.put("attachList", attachList);


        //댓글 리스트
        //List<BoardReplyResponseDto> replyList = replyService.getBoardReplyList(seq, 0);
        replyObj = replyService.getBoardReplyList(seq, 0, 0);
        map.put("replyObj", replyObj);

        return map;
    }


    @Transactional
    @Override
    public void deleteBoard(Long seq) throws Exception {

        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.bohan.man-alive");
        //EntityManager em = emf.createEntityManager();

        //Board board = em.find(Board.class, seq);
        //em.remove(board);

        Board board = boardRepo.getOne(seq);
        board.setUserDetail(null);


        //Board 테이블에서 게시글 삭제
        boardRepo.deleteById(seq);
        //Attach 테이블에서 첨부파일 삭제 + S3에 업로드된 파일 삭제
        attachService.deleteAttach(seq, "boardAttach");
    }

    @Override
    public void getBoardList(BoardCriteria criteria) throws Exception {
        Page<Board> pageInfo = null;

        BooleanBuilder builder = new BooleanBuilder();

        QBoard board = QBoard.board;
        QUser user = QUser.user;

        List<BoardListResponseDto> boardList= queryFactory
                .select(Projections.fields(BoardListResponseDto.class
                        ))
                .from(board)
                .join(user).on(user.email.eq(user.email))
                //.list
                .fetch();




//        if(criteria.getCategory().equals("title")) {
//            builder.and(board.title.like("%" + criteria.getKeyword() + "%"));
//        }else if(criteria.getCategory().equals("content")) {
//            builder.and(board.content.like("%" + criteria.getKeyword() + "%"));
//        }
//
//        Pageable paging = PageRequest.of(criteria.getPageNumber()-1, criteria.getPageAmount(), Sort.Direction.DESC, criteria.getSorting());



    }


}
