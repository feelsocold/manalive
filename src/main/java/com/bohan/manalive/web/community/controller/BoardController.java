package com.bohan.manalive.web.community.controller;

import com.bohan.manalive.config.oauth.LoginUser;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.web.common.dto.AttachDto;
import com.bohan.manalive.web.common.service.AttachService;
import com.bohan.manalive.web.community.domain.Board.Board;
import com.bohan.manalive.web.community.dto.Board.BoardCriteria;
import com.bohan.manalive.web.community.dto.Board.BoardRequestDto;
import com.bohan.manalive.web.community.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final HttpSession httpSession;
    private final BoardService boardService;
    private final AttachService attachService;

    @ResponseBody
    @GetMapping("/test")
    public List<Board> boardtest() throws Exception {
        BoardCriteria criteria = new BoardCriteria();
        return boardService.getBoardList(criteria);
    }

    @GetMapping("")
    public String boardIndex() {
        return "/community/board/board";
    }

    @GetMapping("/write")
    public String boardWrite() {
        if(httpSession.getAttribute("attachList") != null) {
            httpSession.removeAttribute("attachList");
        }
        return "community/board/board_write";
    }

    @Transactional
    @ResponseBody
    @PostMapping("/post")
    public HashMap<String, Object> boardPost(@RequestBody BoardRequestDto boardDto,
                                             @LoginUser SessionUser user, HttpSession session) throws Exception{
        boardDto.setEmail(user.getEmail());
        Long boardSeq = boardService.saveBoard(boardDto, user);

        if(httpSession.getAttribute("attachList") != null) {
            attachService.saveAttachs(boardSeq);
        }
        //return "redirect:/board";
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("boardSeq", boardSeq + "");

        session.removeAttribute("attachList");
        return map;
    }

    @ResponseBody
    @PostMapping("/update")
    public HashMap<String, Object> boardUpdate(@RequestBody BoardRequestDto boardDto,
                            HttpSession session) throws Exception{
        log.info("boardUPdate()");
        Long seq = boardService.updateBoard(boardDto);
        log.info(":::::SEQ : " + seq);

//        if(httpSession.getAttribute("attachList") != null) {
//            attachService.saveAttachs("boardPhoto", boardSeq);
//        }
//        //return "redirect:/board";
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("boardSeq", seq);
//
//        session.removeAttribute("attachList");
        return map;
    }

    @ResponseBody
    @GetMapping("/list")
    public HashMap<String, Object> getBoardList(@ModelAttribute BoardCriteria criteria) throws Exception {

        return boardService.boardListandPaging(criteria);
    }

    @GetMapping("/detail/{seq}")
    public String boardDetail(@PathVariable Long seq, Model model) {
        boardService.increaseReadcount(seq);
        model.addAttribute("seq", seq);
        return "community/board/board_detail";
    }

    @GetMapping("/update/{b_seq}")
    public String updateBoard(@PathVariable Long b_seq, Model model) throws Exception {
        model.addAttribute("seq", b_seq);

        List<AttachDto> list = attachService.getAttachList(b_seq, "BOARD");
        httpSession.setAttribute("attachList", list);
        log.info(list.size() + "<- 세션 리스트 사이즈");

        return "community/board/board_modify";

    }


}
