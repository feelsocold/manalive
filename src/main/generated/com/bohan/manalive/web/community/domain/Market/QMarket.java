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

    public static final QMarket market = new QMarket("market");

    public final ListPath<com.bohan.manalive.web.common.domain.attach.Attach, com.bohan.manalive.web.common.domain.attach.QAttach> attachList = this.<com.bohan.manalive.web.common.domain.attach.Attach, com.bohan.manalive.web.common.domain.attach.QAttach>createList("attachList", com.bohan.manalive.web.common.domain.attach.Attach.class, com.bohan.manalive.web.common.domain.attach.QAttach.class, PathInits.DIRECT2);

    public final StringPath category = createString("category");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath delivery = createString("delivery");

    public final StringPath email = createString("email");

    public final DateTimePath<java.time.LocalDateTime> modifiedDate = createDateTime("modifiedDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final NumberPath<Integer> readCount = createNumber("readCount", Integer.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath state = createString("state");

    public final StringPath title = createString("title");

    public QMarket(String variable) {
        super(Market.class, forVariable(variable));
    }

    public QMarket(Path<? extends Market> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMarket(PathMetadata metadata) {
        super(Market.class, metadata);
    }

}

