package com.bohan.manalive.web.community.domain.UserMarket;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserMarket is a Querydsl query type for UserMarket
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserMarket extends EntityPathBase<UserMarket> {

    private static final long serialVersionUID = 72590731L;

    public static final QUserMarket userMarket = new QUserMarket("userMarket");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final ListPath<com.bohan.manalive.web.community.domain.Market.Market, com.bohan.manalive.web.community.domain.Market.QMarket> marketList = this.<com.bohan.manalive.web.community.domain.Market.Market, com.bohan.manalive.web.community.domain.Market.QMarket>createList("marketList", com.bohan.manalive.web.community.domain.Market.Market.class, com.bohan.manalive.web.community.domain.Market.QMarket.class, PathInits.DIRECT2);

    public final StringPath marketName = createString("marketName");

    public final StringPath marketPhoto = createString("marketPhoto");

    public final DateTimePath<java.time.LocalDateTime> modifiedDate = createDateTime("modifiedDate", java.time.LocalDateTime.class);

    public final StringPath phone = createString("phone");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final NumberPath<Integer> visitedGuestCnt = createNumber("visitedGuestCnt", Integer.class);

    public QUserMarket(String variable) {
        super(UserMarket.class, forVariable(variable));
    }

    public QUserMarket(Path<? extends UserMarket> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserMarket(PathMetadata metadata) {
        super(UserMarket.class, metadata);
    }

}

