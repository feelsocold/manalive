package com.bohan.manalive.web.community.controller;

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

    @GetMapping("")
    public String marketIndex() {
        return "/community/market/market";
    }

    @GetMapping("/sell")
    public String marketSell() {
        if(httpSession.getAttribute("attachList") != null) {
            httpSession.removeAttribute("attachList");
        }
        return "/community/market/market_sell";
    }

    @GetMapping("/detail/{seq}")
    public String marketDetail(@PathVariable Long seq, Model model) {
//        boardService.increaseReadcount(seq);

        model.addAttribute("seq", seq);

        return "community/market/market_detail";
    }

    @GetMapping("/open")
    public String marketOpen() {
        return "/community/market/market_open";
    }

}
