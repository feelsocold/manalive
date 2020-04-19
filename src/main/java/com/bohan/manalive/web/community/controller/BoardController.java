package com.bohan.manalive.web.community.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

    @GetMapping("")
    public String boardList() {
        log.info("board list()");
        return "/community/board/board";
    }

    @GetMapping("/write")
    public String board_write() {
        return "community/board/board_write";
    }


}
