package com.bohan.manalive.web.community.domain.Market;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMarketInquiryAnswer is a Querydsl query type for MarketInquiryAnswer
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMarketInquiryAnswer extends EntityPathBase<MarketInquiryAnswer> {

    private static final long serialVersionUID = 1481975226L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMarketInquiryAnswer marketInquiryAnswer = new QMarketInquiryAnswer("marketInquiryAnswer");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final QMarketInquiry marketInquiry;

    public final NumberPath<Long> marketInquirySeq = createNumber("marketInquirySeq", Long.class);

    public final DateTimePath<java.time.LocalDateTime> modifiedDate = createDateTime("modifiedDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public QMarketInquiryAnswer(String variable) {
        this(MarketInquiryAnswer.class, forVariable(variable), INITS);
    }

    public QMarketInquiryAnswer(Path<? extends MarketInquiryAnswer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMarketInquiryAnswer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMarketInquiryAnswer(PathMetadata metadata, PathInits inits) {
        this(MarketInquiryAnswer.class, metadata, inits);
    }

    public QMarketInquiryAnswer(Class<? extends MarketInquiryAnswer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.marketInquiry = inits.isInitialized("marketInquiry") ? new QMarketInquiry(forProperty("marketInquiry"), inits.get("marketInquiry")) : null;
    }

}

