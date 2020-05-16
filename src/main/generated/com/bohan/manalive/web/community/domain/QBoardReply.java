package com.bohan.manalive.web.community.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardReply is a Querydsl query type for BoardReply
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBoardReply extends EntityPathBase<BoardReply> {

    private static final long serialVersionUID = 1971671719L;

    public static final QBoardReply boardReply = new QBoardReply("boardReply");

    public final com.bohan.manalive.config.oauth.dto.QBaseTimeEntity _super = new com.bohan.manalive.config.oauth.dto.QBaseTimeEntity(this);

    public final NumberPath<Long> b_seq = createNumber("b_seq", Long.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final NumberPath<Long> r_seq = createNumber("r_seq", Long.class);

    public final StringPath replyer = createString("replyer");

    public QBoardReply(String variable) {
        super(BoardReply.class, forVariable(variable));
    }

    public QBoardReply(Path<? extends BoardReply> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardReply(PathMetadata metadata) {
        super(BoardReply.class, metadata);
    }

}

