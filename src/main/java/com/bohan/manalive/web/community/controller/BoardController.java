package com.bohan.manalive.web.community.controller;

import com.bohan.manalive.config.oauth.LoginUser;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.web.common.service.AttachService;
import com.bohan.manalive.web.community.domain.Board;
import com.bohan.manalive.web.community.dto.BoardCriteria;
import com.bohan.manalive.web.community.dto.BoardRequestDto;
import com.bohan.manalive.web.community.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    public String board() {
        return "/community/board/board";
    }

    @GetMapping("/write")
    public String boardWrite() {
        if(httpSession.getAttribute("attachList") != null) {
            httpSession.removeAttribute("attachList");
        }
        return "community/board/board_write";
    }

    @ResponseBody
    @PostMapping("/post")
    public HashMap<String, Object> boardPost(@RequestBody BoardRequestDto boardDto,
                                             @LoginUser SessionUser user, HttpSession session) throws Exception{
        boardDto.setEmail(user.getEmail());
        Long boardSeq = boardService.saveBoard(boardDto, user);

        if(httpSession.getAttribute("attachList") != null) {
            attachService.saveAttachs("boardAttach", boardSeq);
        }
        //return "redirect:/board";
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("boardSeq", boardSeq + "");

        session.removeAttribute("attachList");
        return map;
    }

    @ResponseBody
    @GetMapping("/list")
    public HashMap<String, Object> getBoardList(@ModelAttribute BoardCriteria criteria) throws Exception {

        return boardService.boardListandPaging(criteria);
    }

    @GetMapping("/detail")
    public String boardDetail(String seq, Model model) {
        model.addAttribute("seq", seq);
        return "community/board/board_detail";
    }

    @GetMapping("/modify")
    public String modifyBoard(@RequestParam("b_seq") Long b_seq, Model model) throws Exception {
        //boardService.deleteBoard(b_seq);
        log.info(b_seq + "");
        model.addAttribute("seq", b_seq);
        return "community/board/board_modify";

    }


}
