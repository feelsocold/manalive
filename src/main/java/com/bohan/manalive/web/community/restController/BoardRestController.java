package com.bohan.manalive.web.community.restController;

import com.bohan.manalive.web.community.domain.Board;
import com.bohan.manalive.web.community.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BoardRestController {

    private final BoardService boardService;

    @PostMapping("/boardDetail")
    public Board getBoardDetail(@RequestParam String seq) throws Exception {
        log.info("=======> " + seq);
        log.info(seq);

        return boardService.boardDetail(Long.parseLong(seq));
    }

}
