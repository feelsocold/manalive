package com.bohan.manalive.web.community.domain.Market;

import com.bohan.manalive.web.community.domain.UserMarket.UserMarket;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jdk.vm.ci.code.site.Mark;
import jdk.vm.ci.meta.Local;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString(exclude = "wishedMarketProduct")
@Entity
public class MarketWish implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(nullable = false)
    private Long marketSeq;

    @Column(nullable = false, insertable=true, updatable=false)
    private Long userMarketSeq;

//    @Column(nullable = false, insertable=true, updatable=false)
//    private String email;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @ManyToOne(targetEntity= Market.class, fetch=FetchType.LAZY, cascade = CascadeType.DETACH)
    @JsonBackReference
    @JoinColumn(referencedColumnName = "seq", name="marketSeq", nullable = false, insertable = false, updatable = false)
    private Market wishedMarketProduct;

    @Builder
    public MarketWish(Long marketSeq, Long userMarketSeq, LocalDateTime createDate) {
        this.marketSeq = marketSeq;
        this.userMarketSeq = userMarketSeq;
        this.createDate = createDate;
    }

}
