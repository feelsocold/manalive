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
    @GetMapping("/manage")
    public String marketManage() {
        return "/community/market/market_manage";
    }

    @GetMapping("/detail/{seq}")
    public String marketDetail(@PathVariable Long seq, Model model)throws Exception {
        marketService.increaseMarketReadCount(seq);
        model.addAttribute("seq", seq);
        return "community/market/market_detail";
    }

    @GetMapping("/open")
    public String marketOpen() {
        return "/community/market/market_open";
    }

}
