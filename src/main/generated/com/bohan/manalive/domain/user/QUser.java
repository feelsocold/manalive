package com.bohan.manalive.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 508561580L;

    public static final QUser user = new QUser("user");

    public final com.bohan.manalive.config.oauth.dto.QBaseTimeEntity _super = new com.bohan.manalive.config.oauth.dto.QBaseTimeEntity(this);

    public final ListPath<com.bohan.manalive.web.community.domain.Board, com.bohan.manalive.web.community.domain.QBoard> boardList = this.<com.bohan.manalive.web.community.domain.Board, com.bohan.manalive.web.community.domain.QBoard>createList("boardList", com.bohan.manalive.web.community.domain.Board.class, com.bohan.manalive.web.community.domain.QBoard.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath email = createString("email");

    public final StringPath enable = createString("enable");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final StringPath photo = createString("photo");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath social = createString("social");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

