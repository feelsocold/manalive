package com.bohan.manalive.web.community.domain.UserMarket;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserMarketFollow is a Querydsl query type for UserMarketFollow
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserMarketFollow extends EntityPathBase<UserMarketFollow> {

    private static final long serialVersionUID = -720881380L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserMarketFollow userMarketFollow = new QUserMarketFollow("userMarketFollow");

    public final NumberPath<Long> followerMarketSeq = createNumber("followerMarketSeq", Long.class);

    public final QUserMarket followingUserMarket;

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final NumberPath<Long> userMarketSeq = createNumber("userMarketSeq", Long.class);

    public QUserMarketFollow(String variable) {
        this(UserMarketFollow.class, forVariable(variable), INITS);
    }

    public QUserMarketFollow(Path<? extends UserMarketFollow> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserMarketFollow(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserMarketFollow(PathMetadata metadata, PathInits inits) {
        this(UserMarketFollow.class, metadata, inits);
    }

    public QUserMarketFollow(Class<? extends UserMarketFollow> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.followingUserMarket = inits.isInitialized("followingUserMarket") ? new QUserMarket(forProperty("followingUserMarket")) : null;
    }

}

