package com.bohan.manalive.web.community.dto;

import lombok.Getter;
import lombok.ToString;
import org.hibernate.Criteria;

@Getter
@ToString
public class PageDto {

    private int startPage;
    private int endPage;
    private boolean prev, next;

    private int total;
    private BoardCriteria cri;

    public PageDto(BoardCriteria cri, int total) {
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
