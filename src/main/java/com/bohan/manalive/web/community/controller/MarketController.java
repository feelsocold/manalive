package com.bohan.manalive.web.community.controller;

import com.bohan.manalive.web.community.service.MarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/market")
public class MarketController {
    private final HttpSession httpSession;
    private final MarketService marketService;

    @GetMapping("")
    public String marketIndex() {
        return "/community/market/market";
    }

    @GetMapping("/sell")
    public String marketSell() {
        if(httpSession.getAttribute("attachList") != null) {
            httpSession.removeAttribute("attachList");}
        return "/community/market/market_sell";
    }

    @GetMapping("/detail/{seq}")
    public String marketDetail(@PathVariable Long seq, Model model)throws Exception {
        marketService.increaseMarketReadCount(seq);
        model.addAttribute("seq", seq);
        return "community/market/market_detail";
    }

    @GetMapping("/userMarket/{seq}")
    public String goUserMarket(@PathVariable(name = "seq", required = false) Long seq, Model model){
        if(seq == null){
            seq = 0L;
        }
        model.addAttribute("userSeq", seq);
        return "/community/market/userMarket_main";
    }

    @GetMapping("/open")
    public String marketOpen() {
        if(httpSession.getAttribute("attachList") != null) {
            httpSession.removeAttribute("attachList");}
        return "/community/market/market_open";
    }

}
