package com.bohan.manalive.web.community.controller;

import com.bohan.manalive.config.oauth.LoginUser;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.web.community.dto.BoardSaveRequestDto;
import com.bohan.manalive.web.community.service.AttachService;
import com.bohan.manalive.web.community.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final HttpSession httpSession;
    private final BoardService boardService;
    private final AttachService attachService;

    @GetMapping("")
    public String boardList() {
        return "/community/board/board";
    }

    @GetMapping("/write")
    public String goWrite() {
        if(httpSession.getAttribute("attachList") != null) {
            httpSession.removeAttribute("attachList");
        }
        return "community/board/board_write";
    }

    @PostMapping("/post")
    public String boardPost(BoardSaveRequestDto boardDto, @LoginUser SessionUser user) throws Exception{

        boardDto.setEmail(user.getEmail());
        Long boardSeq = boardService.saveBoard(boardDto, user);
        attachService.saveAttachs("boardAttach", boardSeq);
        return "redirect:/board";
    }


}
