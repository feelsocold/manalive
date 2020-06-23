package com.bohan.manalive.web.community.restController;

import com.bohan.manalive.config.S3Uploader;
import com.bohan.manalive.config.oauth.LoginUser;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.web.common.service.AttachService;
import com.bohan.manalive.web.common.service.AttachSessionService;
import com.bohan.manalive.web.common.service.UserService;
import com.bohan.manalive.web.community.dto.Board.BoardCriteria;
import com.bohan.manalive.web.community.dto.Board.BoardRequestDto;
import com.bohan.manalive.web.community.dto.Market.MarketCriteria;
import com.bohan.manalive.web.community.dto.Market.MarketRequestDto;
import com.bohan.manalive.web.community.dto.UserMarket.UserMarketRequestDto;
import com.bohan.manalive.web.community.service.MarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/market")
@RestController
public class MarketRestController {
    private final HttpSession httpSession;
    private final S3Uploader s3Uploader;
    private final UserService userService;
    private final AttachService attachService;
    private final AttachSessionService attachSessionService;
    private final MarketService marketService;

    @PostMapping("/open/duplicatePhoneCheck")
    public String duplicatePhoneCheck(@RequestBody String phone) {
        log.info(phone);
        return "";
    }

    @PostMapping("/post")
    public HashMap<String, Object> boardPost(@RequestBody MarketRequestDto requestDto,
                                             @LoginUser SessionUser user, HttpSession session) throws Exception{
        requestDto.setEmail(user.getEmail());
        Long marketSeq = marketService.saveMarket(requestDto);

        if(httpSession.getAttribute("attachList") != null) {
            attachService.saveAttachs(marketSeq);
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("seq", marketSeq + "");

        session.removeAttribute("attachList");
        return map;
    }

    @GetMapping("/list")
    public HashMap<String, Object> getMarketList(@ModelAttribute MarketCriteria criteria) throws Exception {
        log.info(criteria.getCategory() + " !@%&^$(*!^@(*&");

        return marketService.getMarketList(criteria);
    }

    @PostMapping("/detail/{seq}")
    public HashMap<String, Object> getMarketDetail(@PathVariable("seq") Long seq) throws Exception {

        log.info("REST getMarketDetail() + " + seq);

        return marketService.getMarketDetail(seq);

    }

    @Transactional
    @PostMapping("/open")
    public HashMap<String, Long> boardPost(@RequestBody UserMarketRequestDto requestDto,
                                             @LoginUser SessionUser user, HttpSession session) throws Exception{
        requestDto.setEmail(user.getEmail());
        Long seq = marketService.openMarket(requestDto);
        if(seq != null) {
            userService.updatePhone(user.getEmail(), requestDto.getPhone());
        }
        HashMap<String, Long> map = new HashMap<>();
        map.put("result", seq);

        if (httpSession.getAttribute("attachList") != null) {
            userService.saveAttach(seq);
        }

        return map;
    }


}
