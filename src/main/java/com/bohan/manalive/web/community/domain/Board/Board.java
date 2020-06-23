package com.bohan.manalive.web.community.domain.Board;

import com.bohan.manalive.domain.user.User;
import com.bohan.manalive.web.common.domain.attach.Attach;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(callSuper = false, exclude = {"user"})
@ToString(exclude = "user, boardLike, attachList")
@Entity
public class Board implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private String hashtags;

    @Column(nullable = true)
    @ColumnDefault("0")
    private int readCount;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = true)
    private LocalDateTime modifiedDate;

    @Column(nullable = false, insertable=true, updatable=false)
    private String email;

//    @ManyToOne(targetEntity=User.class, fetch=FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "email", referencedColumnName = "email", insertable = false, updatable = false)
//    @JsonBackReference
//    private User user;

    //@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    //@Cascade(value = { CascadeType.SAVE_UPDATE, CascadeType.DETACH })
    //@JoinColumn(name = "email", referencedColumnName = "email", insertable = false, updatable = false)


    //@JsonIgnore
    @ManyToOne(targetEntity=User.class, fetch=FetchType.LAZY, cascade = CascadeType.DETACH)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonBackReference
    @JoinColumn(referencedColumnName = "email", name="email", nullable = false, insertable = false, updatable = false)
    private User userDetail;

    @OneToMany(mappedBy="likeBoard", fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardLike> boardLikeList = new ArrayList<>();

    @OneToMany(mappedBy="replyBoard", fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardReply> boardReplyList = new ArrayList<>();

//    @OneToMany(mappedBy="boardAttach", fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Attach> attachList = new ArrayList<>();

    @Builder
    public Board(String title, String content, String hashtags, String email, User userDetail, LocalDateTime createDate) {
        this.title = title;
        this.content = content;
        this.hashtags = hashtags;
        this.email = email;
        this.userDetail = userDetail;
        this.createDate = createDate;
    }

    public Board update(String title, String content, String hashtags, LocalDateTime modifiedDate ){
        this.title = title;
        this.content = content;
        this.hashtags = hashtags;
        this.modifiedDate = modifiedDate;
        return this;
    }

    public Board update(int readCount){
        this.readCount = readCount;
        return this;
    }
}
