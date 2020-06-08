package com.bohan.manalive.web.community.dto.Market;

import com.bohan.manalive.web.community.dto.Board.BoardCriteria;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MarketPageDto {

    private int startPage;
    private int endPage;
    private boolean prev, next;

    private int total;
    private MarketCriteria cri;

    public MarketPageDto(MarketCriteria cri, int total) {
        this.cri = cri;
        this.total = total;

        this.endPage = (int) (Math.ceil(cri.getPageNumber() / 10.0)) * 10;
        this.startPage = this.endPage - 9;

        int realEnd = (int) (Math.ceil((total * 1.0) / cri.getPageAmount()));

        if (realEnd < this.endPage) {
            this.endPage = realEnd;
        }

        this.prev = this.startPage > 1;
        this.next = this.endPage < realEnd;
    }





}
