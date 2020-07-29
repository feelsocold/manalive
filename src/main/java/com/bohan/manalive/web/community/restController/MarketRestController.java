package com.bohan.manalive.web.community.restController;

import com.bohan.manalive.config.S3Uploader;
import com.bohan.manalive.config.oauth.LoginUser;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.domain.user.UserRepository;
import com.bohan.manalive.web.common.service.AttachService;
import com.bohan.manalive.web.common.service.AttachSessionService;
import com.bohan.manalive.web.common.service.UserService;
import com.bohan.manalive.web.community.domain.Market.MarketInquiryAnswer;
import com.bohan.manalive.web.community.domain.UserMarket.UserMarket;
import com.bohan.manalive.web.community.domain.UserMarket.UserMarketRepository;
import com.bohan.manalive.web.community.dto.Board.BoardCriteria;
import com.bohan.manalive.web.community.dto.Board.BoardRequestDto;
import com.bohan.manalive.web.community.dto.Market.*;
import com.bohan.manalive.web.community.dto.UserMarket.UserMarketFollowRequestDto;
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
    private final UserRepository userRepo;
    private final UserMarketRepository userMarketRepo;

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
    public HashMap<String, Object> getMarketDetail(@PathVariable("seq") Long seq, @LoginUser SessionUser user) throws Exception {
        return marketService.getMarketDetail(seq, user);
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
    @PostMapping("/wish/save")
    public Map<String, String> saveMarketWish(@RequestBody MarketWishRequestDto dto, @LoginUser SessionUser user)throws Exception {
        dto.setUserMarketSeq(userMarketRepo.getUserMarketSeq(user.getEmail()));

        Map<String, String> map = new HashMap<>();
        String result = "";
        boolean bool = marketService.checkDuplicatedWish(dto);
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
        //dto.setEmail(user.getEmail());
        dto.setUserMarketSeq(userMarketRepo.getUserMarketSeq(user.getEmail()));
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

    @PostMapping("/userMarket")
    public HashMap<String, Object> getUserMarkerInfo(@RequestParam("seq") Long seq, @LoginUser SessionUser user) throws Exception{
        log.info("getUserMarkerInfo()");
        log.info(seq + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Map<String, Object> map = new HashMap<>();
        return marketService.getUserMarket(seq, user);
    }

    @PostMapping("/getUserMarketSeq")
    public Long getUserSeq(@LoginUser SessionUser user) throws Exception{
        List<UserMarket> list = userMarketRepo.findByEmail(user.getEmail());
        return list.get(0).getSeq();
    }

    @Transactional
    @PostMapping("/follow/{userMarketSeq}")
    public Boolean followUserMarket(@PathVariable("userMarketSeq") Long userMarketSeq, @LoginUser SessionUser user) throws Exception{
        UserMarketFollowRequestDto dto = new UserMarketFollowRequestDto();
        dto.setEmail(user.getEmail());
        dto.setUserMarketSeq(userMarketSeq);
        boolean duplication = marketService.checkDuplicatedFollow(dto);
        if(duplication){
            return marketService.unfollowUserMarket(dto);
        }else{  //팔로우
            return marketService.followUserMarket(dto);
        }
    }

    @PostMapping("/userMarket/wish/{userMarketSeq}")
    public List<MarketWishResponseDto> getUserMarketWishList(@PathVariable("userMarketSeq") Long userMarketSeq, @RequestParam(defaultValue = "0") int pageNumber) throws Exception{
        log.info("getUserMarketWishList() + " + userMarketSeq);

        List<MarketWishResponseDto> list = marketService.getUserMarketWishList(userMarketSeq);
        log.info(list.size() + "!!");
        //return marketService.getUserMarketWishList(userMarketSeq);
        return list;
    }

    @PostMapping("/userMarket/follower/{userMarketSeq}")
    public Map<String, Object> getUserMarketFollowerList(@PathVariable("userMarketSeq") Long userMarketSeq, @RequestParam(defaultValue = "0") int pageNumber) throws Exception{
        return marketService.getUserMarketFollowerList(userMarketSeq, pageNumber);
    }

    @Transactional
    @PostMapping("/userMarket/following/{userMarketSeq}")
    public Map<String, Object> getUserMarketFollowingList(@PathVariable("userMarketSeq") Long userMarketSeq, @RequestParam(defaultValue = "0") int pageNumber) throws Exception{
        return marketService.getUserMarketFollowingList(userMarketSeq, pageNumber);
    }

    @PostMapping("/checkUserMarketFollow")
    public Boolean checkUserMarketFollow(@RequestParam Long userMarketSeq, @LoginUser SessionUser user)throws Exception {
        UserMarketFollowRequestDto dto = new UserMarketFollowRequestDto();
        dto.setEmail(user.getEmail());
        dto.setUserMarketSeq(userMarketSeq);
        return marketService.checkDuplicatedFollow(dto);
    }

    @PostMapping("/checkHavingUserMarket")
    public Boolean checkHavingUserMarket(@LoginUser SessionUser user)throws Exception {
        List<UserMarket> list = userMarketRepo.findByEmail(user.getEmail());
        return list.size()>0 ? true : false;
    }

    @PostMapping("/saveMarketInquiry")
    public boolean saveMarketInquiry(@RequestBody MarketInquiryRequestDto dto,
                                     @LoginUser SessionUser user) throws Exception{
        return marketService.saveMarketInquiry(dto, user);
    }

    @PostMapping("/inquiry/list/{marketProductSeq}/{pageNumber}/{delayCnt}")
    public Map<String, Object> getInquiryList(@PathVariable(name = "marketProductSeq") Long seq,
                                                         @PathVariable(name = "pageNumber") int inquiryPageNumber,
                                                         @PathVariable(name = "delayCnt") int delayCnt
                                                         )throws Exception{
        return marketService.getMarketInquiryList(seq, inquiryPageNumber, delayCnt);
    }

    @PostMapping("inquiry/delete/{seq}")
    public void deleteInquiry(@PathVariable(name = "seq") Long inquirySeq)throws Exception{
        marketService.deleteMarketInquiry(inquirySeq);
    }

    @PostMapping("/saveMarketInquiryAnswer")
    public MarketInquiryAnswer saveMarketInquiryAnswer(@RequestBody MarketInquiryAnswerRequestDto dto) throws Exception{
        return marketService.saveMarketInquiryAnswer(dto);
    }

    @PostMapping("/inquiry/answer/{marketInquirySeq}")
    public List<MarketInquiryAnswer> getMarketInquiryAnswer(@PathVariable(name="marketInquirySeq") Long marketInquirySeq) throws Exception{
        return marketService.getMarketInquiryAnswerList(marketInquirySeq);
    }
}
