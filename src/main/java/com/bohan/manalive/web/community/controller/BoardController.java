package com.bohan.manalive.web.community.controller;

import com.bohan.manalive.web.community.domain.AttachSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final HttpSession httpSession;

    @GetMapping("")
    public String boardList() {
        log.info("board list()");
        return "/community/board/board";
    }

    @GetMapping("/write")
    public String board_write() {
        log.info("board_write()");
        if(httpSession.getAttribute("attachList") != null) {

            httpSession.removeAttribute("attachList");
            log.info("session remove~");



        }else{
            log.info("session doesnt exist");
        }

        return "community/board/board_write";
    }


}
