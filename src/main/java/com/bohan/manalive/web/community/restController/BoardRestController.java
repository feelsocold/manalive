package com.bohan.manalive.web.community.restController;

import com.bohan.manalive.config.oauth.LoginUser;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.web.common.service.AttachService;
import com.bohan.manalive.web.community.dto.BoardLikeRequestDto;
import com.bohan.manalive.web.community.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BoardRestController {

    private final BoardService boardService;
    private final AttachService attachService;

    @PostMapping("/boardDetail")
    public HashMap<String, Object> getBoardDetail(@RequestParam String seq) throws Exception {
        log.info("=======> " + seq);
        log.info(seq);

        return boardService.boardDetail(Long.parseLong(seq));
    }

    @PostMapping("/boardDelete")
    public void deleteBoard(@RequestParam("b_seq") Long b_seq) throws Exception {
        //attachService.deleteAttach(b_seq, "boardAttach");
        boardService.deleteBoard(b_seq);
    }



    @PostMapping("/boardLike")
    public void boardDoLike(@RequestParam("b_seq") Long b_seq, @LoginUser SessionUser user) throws Exception {
        BoardLikeRequestDto dto = new BoardLikeRequestDto();
        dto.setB_seq(b_seq);
        dto.setEmail(user.getEmail());
        boardService.doLikeBoard(dto);
    }

    @PostMapping("/boardUnLike")
    public void boardDoUnLike(@RequestParam("b_seq") Long b_seq, @LoginUser SessionUser user) throws Exception {
        BoardLikeRequestDto dto = new BoardLikeRequestDto();
        dto.setB_seq(b_seq);
        dto.setEmail(user.getEmail());
        boardService.doLikeBoard(dto);
    }

    @PostMapping("/boardLikeDiscern")
    public boolean boardLikeDiscern(@RequestParam("b_seq") Long b_seq, @LoginUser SessionUser user) throws Exception{
        BoardLikeRequestDto dto = new BoardLikeRequestDto();
        dto.setB_seq(b_seq);
        dto.setEmail(user.getEmail());
        return boardService.discernLikeBoard(dto);
    }

}
