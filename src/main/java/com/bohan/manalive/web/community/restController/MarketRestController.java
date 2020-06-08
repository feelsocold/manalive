package com.bohan.manalive.web.community.restController;

import com.bohan.manalive.config.S3Uploader;
import com.bohan.manalive.config.oauth.LoginUser;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.web.common.service.AttachService;
import com.bohan.manalive.web.common.service.AttachSessionService;
import com.bohan.manalive.web.community.dto.Board.BoardCriteria;
import com.bohan.manalive.web.community.dto.Board.BoardRequestDto;
import com.bohan.manalive.web.community.dto.Market.MarketCriteria;
import com.bohan.manalive.web.community.dto.Market.MarketRequestDto;
import com.bohan.manalive.web.community.service.MarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/market")
@RestController
public class MarketRestController {
    private final HttpSession httpSession;
    private final S3Uploader s3Uploader;
    private final AttachService attachService;
    private final AttachSessionService attachSessionService;
    private final MarketService marketService;


    @PostMapping("/post")
    public HashMap<String, Object> boardPost(@RequestBody MarketRequestDto requestDto,
                                             @LoginUser SessionUser user, HttpSession session) throws Exception{
        requestDto.setEmail(user.getEmail());
        Long marketSeq = marketService.saveBoard(requestDto);

        if(httpSession.getAttribute("attachList") != null) {
            attachService.saveAttachs("marketPhoto", marketSeq);
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("seq", marketSeq + "");

        session.removeAttribute("attachList");
        return map;
    }

    @GetMapping("/list")
    public HashMap<String, Object> getBoardList(@ModelAttribute MarketCriteria criteria) throws Exception {
        log.info(criteria.getCategory() + " !@%&^$(*!^@(*&");

        return marketService.getMarketList(criteria);
    }

}
