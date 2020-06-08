package com.bohan.manalive.web.community.dto.Board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BoardCriteria {

    private int pageNumber;
    private int pageAmount;
    //정렬
    private String sorting = "seq";
    private String sortingDirection;
    //검색
    private String category;
    private String keyword;

    public BoardCriteria() {
        this(1,15);
    }
    public BoardCriteria(int pageNumber, int pageAmount){
        this.pageNumber = pageNumber;
        this.pageAmount = pageAmount;
    }


    public BoardCriteria(int pageNumber, int pageAmount, String sorting, String sortingDirection, String category, String keyword) {
        this.pageNumber = pageNumber;
        this.pageAmount = pageAmount;
        this.sorting = sorting;
        this.sortingDirection = sortingDirection;
        this.category = category;
        this.keyword = keyword;
    }





}
