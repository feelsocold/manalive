package com.bohan.manalive.web.community.controller;

import com.bohan.manalive.web.community.domain.AttachSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
        return "/community/board/board";
    }

    @GetMapping("/write")
    public String goWrite() {
        if(httpSession.getAttribute("attachList") != null) {
            httpSession.removeAttribute("attachList");
        }
        return "community/board/board_write";
    }

    @PostMapping("/write")
    public void boardWrite(String title, String content, String hashtags) {
        log.info(title);
        log.info(content);
        log.info(hashtags);
    }


}
