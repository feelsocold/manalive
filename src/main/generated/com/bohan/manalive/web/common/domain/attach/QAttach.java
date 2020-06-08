package com.bohan.manalive.web.common.domain.attach;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttach is a Querydsl query type for Attach
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAttach extends EntityPathBase<Attach> {

    private static final long serialVersionUID = 1878436069L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttach attach = new QAttach("attach");

    public final com.bohan.manalive.config.oauth.dto.QBaseTimeEntity _super = new com.bohan.manalive.config.oauth.dto.QBaseTimeEntity(this);

    public final NumberPath<Long> att_no = createNumber("att_no", Long.class);

    public final StringPath category = createString("category");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath extension = createString("extension");

    public final StringPath filename = createString("filename");

    public final com.bohan.manalive.web.community.domain.Market.QMarket marketAttach;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final NumberPath<Long> superKey = createNumber("superKey", Long.class);

    public final StringPath url = createString("url");

    public final StringPath uuid = createString("uuid");

    public QAttach(String variable) {
        this(Attach.class, forVariable(variable), INITS);
    }

    public QAttach(Path<? extends Attach> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttach(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttach(PathMetadata metadata, PathInits inits) {
        this(Attach.class, metadata, inits);
    }

    public QAttach(Class<? extends Attach> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.marketAttach = inits.isInitialized("marketAttach") ? new com.bohan.manalive.web.community.domain.Market.QMarket(forProperty("marketAttach")) : null;
    }

}

