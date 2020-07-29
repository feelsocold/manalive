package com.bohan.manalive.web.community.domain.Market;

import com.bohan.manalive.web.community.domain.UserMarket.UserMarket;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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
public class MarketInquiry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(nullable = false)
    Long marketProductSeq;

    @Column(nullable = false)
    private Long userMarketSeq;

    @Column(nullable = false)
    private String content;

//    @Column(insertable = false, columnDefinition = "TINYINT(1) default '0'")
//    private Boolean answerStatus;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = true)
    private LocalDateTime modifiedDate;

    @ManyToOne(targetEntity= Market.class, fetch=FetchType.LAZY, cascade = CascadeType.DETACH)
    @JsonBackReference
    @JoinColumn(referencedColumnName = "seq", name="marketProductSeq", nullable = false, insertable = false, updatable = false)
    private Market inquiredMarket;

    @OneToMany(mappedBy="marketInquiry", fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MarketInquiryAnswer> MarketInquiryAnswerList = new ArrayList<>();

    @Builder
    public MarketInquiry(Long marketProductSeq, Long userMarketSeq, String content, LocalDateTime createDate) {
        this.marketProductSeq = marketProductSeq;
        this.userMarketSeq = userMarketSeq;
        this.content = content;
        this.createDate = createDate;
    }


//    public MarketInquiry updateReadCount(int readCount){
//        this.readCount = readCount;
//        return this;
//    }
}
