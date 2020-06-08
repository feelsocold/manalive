package com.bohan.manalive.web.community.dto.Market;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MarketCriteria {

    private int pageNumber;
    private int pageAmount;
    //정렬
    private String sorting = "seq";
    private String sortingDirection;
    //검색
    private String m_category;
    private String category;
    private String keyword;

    public MarketCriteria() {
        this(1,12);
    }
    public MarketCriteria(int pageNumber, int pageAmount){
        this.pageNumber = pageNumber;
        this.pageAmount = pageAmount;
    }


    public MarketCriteria(int pageNumber, int pageAmount, String sorting, String sortingDirection, String category, String keyword) {
        this.pageNumber = pageNumber;
        this.pageAmount = pageAmount;
        this.sorting = sorting;
        this.sortingDirection = sortingDirection;
        this.category = category;
        this.keyword = keyword;
    }





}
