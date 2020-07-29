package com.bohan.manalive.web.common.domain.attach;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAttach is a Querydsl query type for Attach
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAttach extends EntityPathBase<Attach> {

    private static final long serialVersionUID = 1878436069L;

    public static final QAttach attach = new QAttach("attach");

    public final com.bohan.manalive.config.oauth.dto.QBaseTimeEntity _super = new com.bohan.manalive.config.oauth.dto.QBaseTimeEntity(this);

    public final NumberPath<Long> attNo = createNumber("attNo", Long.class);

    public final StringPath category = createString("category");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath extension = createString("extension");

    public final StringPath filename = createString("filename");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final NumberPath<Long> superKey = createNumber("superKey", Long.class);

    public final StringPath url = createString("url");

    public final StringPath uuid = createString("uuid");

    public QAttach(String variable) {
        super(Attach.class, forVariable(variable));
    }

    public QAttach(Path<? extends Attach> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAttach(PathMetadata metadata) {
        super(Attach.class, metadata);
    }

}

