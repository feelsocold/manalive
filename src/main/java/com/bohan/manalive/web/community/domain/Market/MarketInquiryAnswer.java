package com.bohan.manalive.web.community.domain.Market;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(callSuper = false, exclude = {"user"})
@Entity
public class MarketInquiryAnswer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(nullable = false)
    Long marketInquirySeq;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = true)
    private LocalDateTime modifiedDate;

//    @ManyToOne(targetEntity= Market.class, fetch=FetchType.LAZY, cascade = CascadeType.DETACH)
//    @JsonBackReference
//    @JoinColumn(referencedColumnName = "seq", name="marketProductSeq", nullable = false, insertable = false, updatable = false)
//    private MarketInquiryAnswer inquiredMarket;

//    @ManyToOne(targetEntity= UserMarket.class, fetch=FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonBackReference
//    @JoinColumn(referencedColumnName = "email", name="email", nullable = false, insertable = false, updatable = false)
//    //@Where(clause = "category = 'marketPhoto'")
//    private UserMarket userMarket;
//
    @ManyToOne(targetEntity= MarketInquiry.class, fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(referencedColumnName = "seq", name="marketInquirySeq", nullable = false, insertable = false, updatable = false)
    //@Where(clause = "category = 'marketPhoto'")
    private MarketInquiry marketInquiry;


    @Builder
    public MarketInquiryAnswer(Long marketInquirySeq, String content, LocalDateTime createDate) {
        this.marketInquirySeq = marketInquirySeq;
        this.content = content;
        this.createDate = createDate;
    }

}
