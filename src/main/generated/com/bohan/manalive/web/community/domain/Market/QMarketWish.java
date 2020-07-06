package com.bohan.manalive.web.community.domain.Market;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMarketWish is a Querydsl query type for MarketWish
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMarketWish extends EntityPathBase<MarketWish> {

    private static final long serialVersionUID = 887606194L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMarketWish marketWish = new QMarketWish("marketWish");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> marketSeq = createNumber("marketSeq", Long.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final QMarket wishedMarketProduct;

    public QMarketWish(String variable) {
        this(MarketWish.class, forVariable(variable), INITS);
    }

    public QMarketWish(Path<? extends MarketWish> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMarketWish(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMarketWish(PathMetadata metadata, PathInits inits) {
        this(MarketWish.class, metadata, inits);
    }

    public QMarketWish(Class<? extends MarketWish> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.wishedMarketProduct = inits.isInitialized("wishedMarketProduct") ? new QMarket(forProperty("wishedMarketProduct"), inits.get("wishedMarketProduct")) : null;
    }

}

