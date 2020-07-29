package com.bohan.manalive.web.community.domain.Market;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMarketInquiry is a Querydsl query type for MarketInquiry
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMarketInquiry extends EntityPathBase<MarketInquiry> {

    private static final long serialVersionUID = -835875108L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMarketInquiry marketInquiry = new QMarketInquiry("marketInquiry");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final QMarket inquiredMarket;

    public final ListPath<MarketInquiryAnswer, QMarketInquiryAnswer> MarketInquiryAnswerList = this.<MarketInquiryAnswer, QMarketInquiryAnswer>createList("MarketInquiryAnswerList", MarketInquiryAnswer.class, QMarketInquiryAnswer.class, PathInits.DIRECT2);

    public final NumberPath<Long> marketProductSeq = createNumber("marketProductSeq", Long.class);

    public final DateTimePath<java.time.LocalDateTime> modifiedDate = createDateTime("modifiedDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final NumberPath<Long> userMarketSeq = createNumber("userMarketSeq", Long.class);

    public QMarketInquiry(String variable) {
        this(MarketInquiry.class, forVariable(variable), INITS);
    }

    public QMarketInquiry(Path<? extends MarketInquiry> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMarketInquiry(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMarketInquiry(PathMetadata metadata, PathInits inits) {
        this(MarketInquiry.class, metadata, inits);
    }

    public QMarketInquiry(Class<? extends MarketInquiry> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.inquiredMarket = inits.isInitialized("inquiredMarket") ? new QMarket(forProperty("inquiredMarket"), inits.get("inquiredMarket")) : null;
    }

}

