package com.bohan.manalive.web.community.restController;

import com.bohan.manalive.config.oauth.LoginUser;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.web.community.dto.BoardReplyResponseDto;
import com.bohan.manalive.web.community.dto.BoardReplySaveRequestDto;
import com.bohan.manalive.web.community.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
//@RequestMapping("/reply")
@RestController
public class ReplyRestContorller {

    private final ReplyService replyService;

    @PostMapping("/reply/board/save")
    public Map<String, Object> saveBoardReply(@RequestBody BoardReplySaveRequestDto dto
                                                      ,@LoginUser SessionUser user) {
        dto.setReplyer(user.getEmail());
        replyService.saveBoardReply(dto);

        return replyService.getBoardReplyList(dto.getB_seq(), 0);
    }

    @PostMapping("/reply/board/list")
    public Map<String, Object> getBoardList(@RequestParam Long b_seq, int pageNumber){
        return replyService.getBoardReplyList(b_seq, pageNumber);
    }


}
