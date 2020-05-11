package com.bohan.manalive.web.community.controller;

import com.bohan.manalive.config.oauth.LoginUser;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.web.community.dto.*;
import com.bohan.manalive.web.community.service.AttachService;
import com.bohan.manalive.web.community.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final HttpSession httpSession;
    private final BoardService boardService;
    private final AttachService attachService;

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
    public HashMap<String, Object> boardPost(@RequestBody BoardRequestDto boardDto, @LoginUser SessionUser user) throws Exception{
        boardDto.setEmail(user.getEmail());
        Long boardSeq = boardService.saveBoard(boardDto, user);

        if(httpSession.getAttribute("attachList") != null) {
            attachService.saveAttachs("boardAttach", boardSeq);
        }
        //return "redirect:/board";
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("boardSeq", boardSeq + "");
        return map;
    }

    @ResponseBody
    @GetMapping("/list")
    public HashMap<String, Object> getBoardList(@ModelAttribute BoardCriteria criteria) throws Exception {
        log.info(criteria.toString());
        return boardService.boardListandPaging(criteria);
    }

    @GetMapping("/detail")
    public String boardDetail(String seq, Model model) {

        model.addAttribute("seq", seq);

        return "community/board/board_detail";
    }


}
