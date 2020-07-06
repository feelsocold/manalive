package com.bohan.manalive.web.community.domain.Market;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMarket is a Querydsl query type for Market
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMarket extends EntityPathBase<Market> {

    private static final long serialVersionUID = -714812309L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMarket market = new QMarket("market");

    public final StringPath category = createString("category");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath delivery = createString("delivery");

    public final StringPath email = createString("email");

    public final ListPath<MarketWish, QMarketWish> marketWishList = this.<MarketWish, QMarketWish>createList("marketWishList", MarketWish.class, QMarketWish.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> modifiedDate = createDateTime("modifiedDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath productStatus = createString("productStatus");

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final NumberPath<Integer> readCount = createNumber("readCount", Integer.class);

    public final StringPath saleStatus = createString("saleStatus");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath title = createString("title");

    public final com.bohan.manalive.web.community.domain.UserMarket.QUserMarket userMarket;

    public QMarket(String variable) {
        this(Market.class, forVariable(variable), INITS);
    }

    public QMarket(Path<? extends Market> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMarket(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMarket(PathMetadata metadata, PathInits inits) {
        this(Market.class, metadata, inits);
    }

    public QMarket(Class<? extends Market> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userMarket = inits.isInitialized("userMarket") ? new com.bohan.manalive.web.community.domain.UserMarket.QUserMarket(forProperty("userMarket")) : null;
    }

}

