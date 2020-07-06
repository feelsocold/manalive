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
import com.bohan.manalive.web.community.dto.Market.MarketWishRequestDto;
import com.bohan.manalive.web.community.dto.UserMarket.UserMarketRequestDto;
import com.bohan.manalive.web.community.dto.UserMarket.UserMarketResponseDto;
import com.bohan.manalive.web.community.service.MarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return marketService.getMarketList(criteria);
    }

    @PostMapping("/detail/{seq}")
    public HashMap<String, Object> getMarketDetail(@PathVariable("seq") Long seq) throws Exception {
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

    @Transactional
    @PostMapping("/wish")
    public Map<String, String> saveMarketWish(@RequestBody MarketWishRequestDto dto, @LoginUser SessionUser user)throws Exception {
        dto.setEmail(user.getEmail());
        Map<String, String> map = new HashMap<>();
        String result = "";
        boolean  bool = marketService.checkDuplicatedWish(dto);
        log.info("위시 중복체크 : " + bool);
        if(bool) {
            marketService.deleteMarketWish(dto);
            result = "DELETE";
        }else{
            marketService.saveMarketWish(dto);
            result = "SAVE";
        }
        map.put("result", result);
        return map;
    }

    @PostMapping("/wishCheck")
    public Map<String, Boolean> checkMarketWish(@RequestBody MarketWishRequestDto dto, @LoginUser SessionUser user)throws Exception {
        dto.setEmail(user.getEmail());
        log.info("WISHCHECK()");
        Map<String, Boolean> map = new HashMap<>();
        map.put("result", marketService.checkDuplicatedWish(dto));
        return map;
    }

    @GetMapping("/autoSearching")
    public String[] marketAutoSearching(@RequestParam("searchValue") String searchValue) throws Exception {
        List<String> list = new ArrayList<String>();
        list = marketService.autoSearchMarket(searchValue);
        log.info("검색 리스트 사이즈 : " + list.size());
        String[] array = list.toArray(new String[list.size()]);
        return array;
        //return marketService.autoSearchMarket(searchValue);
    }

    @GetMapping("/search/userMarketName")
    public HashMap<String, Object> searchUserMarketName(@RequestParam("searchValue") String searchValue,
                                                        @RequestParam(defaultValue = "0") int pageNumber) throws Exception {
        return marketService.searchUserMarketName(searchValue, pageNumber);
        //return marketService.autoSearchMarket(searchValue);
    }

    @GetMapping("/market/manage")
    public HashMap<String, Object> getUserMarkerInfo(@LoginUser SessionUser user){
        Map<String, Object> map = new HashMap<>();




        return null;
    }




}
