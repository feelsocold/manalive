package com.bohan.manalive.web.community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/market")
public class MarketController {

    @GetMapping("")
    public String marketIndex() {
        return "/community/market/market";
    }

    @GetMapping("/sell")
    public String marketSell() {
        return "/community/market/market_sell";
    }


}
