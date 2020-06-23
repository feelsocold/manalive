package com.bohan.manalive.web.community.service.impl;

import com.bohan.manalive.config.S3Uploader;
import com.bohan.manalive.config.oauth.LoginUser;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.domain.user.UserRepository;
import com.bohan.manalive.web.common.domain.attach.Attach;
import com.bohan.manalive.web.common.domain.attach.AttachRepository;
import com.bohan.manalive.web.common.dto.AttachDto;
import com.bohan.manalive.web.common.dto.AttachResponseDto;
import com.bohan.manalive.web.common.service.AttachService;
import com.bohan.manalive.web.community.domain.Board.Board;
import com.bohan.manalive.web.community.domain.Board.BoardLikeRepository;
import com.bohan.manalive.web.community.domain.Board.BoardRepository;
import com.bohan.manalive.web.community.domain.Board.BoardSpecs;
import com.bohan.manalive.web.community.dto.Board.*;
import com.bohan.manalive.web.community.service.BoardService;
import com.bohan.manalive.web.community.service.ReplyService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private final BoardLikeRepository boardLikeRepo;
    private final ReplyService replyService;
    private final AttachService attachService;
    private final AttachRepository attachRepo;
    private final HttpSession httpSession;
    private final S3Uploader s3Uploader;
    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
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
        log.info("페이지넘버 : " + criteria.getPageNumber() + "!!!!!");
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
        log.info(boardList.size() + " <- 리스트 사이즈");

        List<BoardUserDetailDto> detailList = new ArrayList<>();
        for(Board BoardDto : boardList){
            BoardUserDetailDto userDetail = new BoardUserDetailDto();
            userDetail.setNickname(BoardDto.getUserDetail().getNickname());
            userDetail.setPhoto(BoardDto.getUserDetail().getPhoto());
            detailList.add(userDetail);
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("userDetail", detailList);
        map.put("boardList", boardList);
        map.put("pageMaker", new PageDto(criteria, (int)(long)total ));

        return map;
    }

    @Transactional
    @Override
    public HashMap<String, Object> boardDetail(Long seq) {
        HashMap<String, Object> map = new HashMap<>();
        //Map<String, Object> replyObj = new HashMap<>();

        BoardResponseDto boardDto = boardRepo.getBoardDetail(seq);
        map.put("boardDto", boardDto);

        List<AttachResponseDto> attachList = boardRepo.getBoardAttachList(seq);
        map.put("attachList", attachList);

        // 댓글 리스트
        //List<BoardReplyResponseDto> replyList = replyService.getBoardReplyList(seq, 0);
        Map<String, Object> replyObj = replyService.getBoardReplyList(seq, 0, 0);
        map.put("replyObj", replyObj);

        // 좋아요 수
        Long likeCnt = boardLikeRepo.likeCnt(seq);
         map.put("likeCnt", likeCnt);

        return map;
    }

    @Transactional
    @Override
    public void deleteBoard(Long seq) throws Exception {

        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.bohan.man-alive");
        //EntityManager em = emf.createEntityManager();

        //Board board = em.find(Board.class, seq);
        //em.remove(board);

//        Board board = boardRepo.getOne(seq);
//        board.setUserDetail(null);


        //Board 테이블에서 게시글 삭제
        //boardRepo.deleteById(seq);
        boardRepo.deleteBoard(seq);
    }

    @Override
    public Long updateBoard(BoardRequestDto dto) throws Exception {
        log.info(dto.toString());


        Board board = boardRepo.findById(dto.getSeq())
                .map(entity -> entity.update(dto.getTitle(),
                                            dto.getContent(),
                                            dto.getHashtags(), currentDateTime)).get();

        // 첨부파일 수정
        List<AttachDto> savedList = attachService.getAttachList(dto.getSeq(), "boardPhoto");
        List<AttachDto> sessionList = (List<AttachDto>)httpSession.getAttribute("attachList");

        for(int i = 0; i < savedList.size(); i++){
            AttachDto sessionDto = (AttachDto)sessionList.get(i);
            AttachDto savedDto =  (AttachDto)savedList.get(i);
            //수정
            if(!sessionList.get(i).getFilename().equals("") && !sessionList.get(i).getFilename().equals(savedList.get(i).getFilename())){
                Attach attach = attachRepo.findById(savedList.get(i).getAtt_no())
                        .map(entity -> entity.update(sessionDto.getFilename(),
                                                    sessionDto.getExtension(),
                                                    sessionDto.getUuid(),
                                                    sessionDto.getUrl()
                                                       )).get();
                //S3에 업로드된 파일 삭제
                String dateDir = formatter.format(savedDto.getModifiedDate());
                String dirName = savedDto.getCategory() + "/" + dateDir;
                String fileName = savedDto.getUuid() + "_" + savedDto.getFilename() +"." + savedDto.getExtension();
                s3Uploader.deleteFile(dirName, fileName);
            }
            //삭제
            else if(sessionList.get(i).getFilename().equals("")){
                attachService.deleteAttachOne(sessionDto.getAtt_no(), sessionDto.getCategory());
            }
        }

        for(AttachDto sessionDto : sessionList){
            //새로 추가
            if(sessionDto.getAtt_no() == null){
                sessionDto.setSuperKey(dto.getSeq());
                sessionDto.setCategory("BOARD");
                attachRepo.save(sessionDto.toEntity().builder()
                                    .category(sessionDto.getCategory())
                                    .superKey(dto.getSeq())
                                    .filename(sessionDto.getFilename())
                                    .extension(sessionDto.getExtension())
                                    .uuid(sessionDto.getUuid())
                                    .url(sessionDto.getUrl())
                                       .build());
            }
//            //삭제
//            if(sessionDto.getFilename().equals("")){
//                attachService.deleteAttachOne(sessionDto.getAtt_no(), sessionDto.getCategory());
//            }

        }
        return board.getSeq();
    }

    @Transactional
    @Override
    public List<Board> getBoardList(BoardCriteria criteria) throws Exception {
//        Page<Board> pageInfo = null;
//
//        BooleanBuilder builder = new BooleanBuilder();
//
//        QBoard board = QBoard.board;
//        QUser user = QUser.user;
//
//        builder.and(board.title.like("%" + "1313" + "%"));
//
//        List<BoardListResponseDto> boardList = queryFactory
//                .select(Projections.fields(BoardListResponseDto.class,
//                        board.seq.as("seq"),
//                        board.email.as("email"),
//                        user.nickname.as("nickname")
//                        ))
//                .from(board)
//                .join(user).on(board.email.eq(user.email))
//                .fetch();
//
//
//        for(BoardListResponseDto dto : boardList){
//            log.info(dto.getSeq() + " + " + dto.getNickname());
//        }






//        if(criteria.getCategory().equals("title")) {
//            builder.and(board.title.like("%" + criteria.getKeyword() + "%"));
//        }else if(criteria.getCategory().equals("content")) {
//            builder.and(board.content.like("%" + criteria.getKeyword() + "%"));
//        }
//
//        Pageable paging = PageRequest.of(criteria.getPageNumber()-1, criteria.getPageAmount(), Sort.Direction.DESC, criteria.getSorting());



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


        return boardList;

    }

    @Override
    public void doLikeBoard(BoardLikeRequestDto requestDto) throws Exception {
        boardLikeRepo.save(requestDto.toEntity());
    }

    @Override
    public void doUnLikeBoard(BoardLikeRequestDto dto) throws Exception {
        //boardLikeRepo.deleteByEmailAndB_seq(dto.getEmail(), dto.getB_seq());
        boardLikeRepo.unLike(dto.getB_seq(), dto.getEmail());
    }

    @Override
    public boolean discernLikeBoard(BoardLikeRequestDto dto) throws Exception {
        int a = boardLikeRepo.discernBoardLike(dto.getB_seq(), dto.getEmail());
        return a>0 ? true : false;
    }

    @Override
    public void increaseReadcount(Long seq) {
        Board board = boardRepo.findById(seq).get();
        int beforeCnt = board.getReadCount();

        boardRepo.findById(seq)
                .map(entity -> entity.update(beforeCnt+1));
    }

}
