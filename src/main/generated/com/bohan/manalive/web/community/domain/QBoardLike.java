package com.bohan.manalive.web.community.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardLike is a Querydsl query type for BoardLike
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBoardLike extends EntityPathBase<BoardLike> {

    private static final long serialVersionUID = 479069242L;

    public static final QBoardLike boardLike = new QBoardLike("boardLike");

    public final com.bohan.manalive.config.oauth.dto.QBaseTimeEntity _super = new com.bohan.manalive.config.oauth.dto.QBaseTimeEntity(this);

    public final NumberPath<Long> b_seq = createNumber("b_seq", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath email = createString("email");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public QBoardLike(String variable) {
        super(BoardLike.class, forVariable(variable));
    }

    public QBoardLike(Path<? extends BoardLike> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardLike(PathMetadata metadata) {
        super(BoardLike.class, metadata);
    }

}

