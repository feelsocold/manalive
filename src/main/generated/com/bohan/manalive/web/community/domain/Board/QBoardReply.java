package com.bohan.manalive.web.community.domain.Board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardReply is a Querydsl query type for BoardReply
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBoardReply extends EntityPathBase<BoardReply> {

    private static final long serialVersionUID = -1697143665L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardReply boardReply = new QBoardReply("boardReply");

    public final com.bohan.manalive.config.oauth.dto.QBaseTimeEntity _super = new com.bohan.manalive.config.oauth.dto.QBaseTimeEntity(this);

    public final NumberPath<Long> b_seq = createNumber("b_seq", Long.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final NumberPath<Long> r_seq = createNumber("r_seq", Long.class);

    public final QBoard replyBoard;

    public final StringPath replyer = createString("replyer");

    public QBoardReply(String variable) {
        this(BoardReply.class, forVariable(variable), INITS);
    }

    public QBoardReply(Path<? extends BoardReply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardReply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardReply(PathMetadata metadata, PathInits inits) {
        this(BoardReply.class, metadata, inits);
    }

    public QBoardReply(Class<? extends BoardReply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.replyBoard = inits.isInitialized("replyBoard") ? new QBoard(forProperty("replyBoard"), inits.get("replyBoard")) : null;
    }

}

