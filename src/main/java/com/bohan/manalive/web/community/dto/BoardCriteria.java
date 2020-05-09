package com.bohan.manalive.web.community.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class BoardCriteria {

    private int pageNumber = 0;
    private int pageAmount = 15;
    private String sorting;
    private String category;
    private String keyword;

    public BoardCriteria(int pageNumber, int pageAmount, String sorting, String category, String keyword) {
        this.pageNumber = pageNumber;
        this.pageAmount = pageAmount;
        this.sorting = sorting;
        this.category = category;
        this.keyword = keyword;
    }





}
