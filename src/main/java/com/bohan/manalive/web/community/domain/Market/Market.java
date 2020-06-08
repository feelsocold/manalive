package com.bohan.manalive.web.community.domain.Market;

import com.bohan.manalive.domain.user.User;
import com.bohan.manalive.web.common.domain.attach.Attach;
import com.bohan.manalive.web.community.domain.Board.BoardLike;
import com.bohan.manalive.web.community.domain.Board.BoardReply;
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
@ToString(exclude = "attachList")
@Entity
public class Market implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String delivery;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = true)
    @ColumnDefault("0")
    private int readCount;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = true)
    private LocalDateTime modifiedDate;

    @Column(nullable = false, insertable=true, updatable=false)
    private String email;

    //@JsonIgnore
//    @ManyToOne(targetEntity=User.class, fetch=FetchType.LAZY, cascade = CascadeType.DETACH)
//    @OnDelete(action = OnDeleteAction.NO_ACTION)
//    @JsonBackReference
//    @JoinColumn(referencedColumnName = "email", name="email", nullable = false, insertable = false, updatable = false)
//    private User userDetail;

    @OneToMany(mappedBy="marketAttach", fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Where(clause = "category = 'marketPhoto'")
    private List<Attach> attachList = new ArrayList<>();
//
//    @OneToMany(mappedBy="replyBoard", fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<BoardReply> boardReplyList = new ArrayList<>();

    @Builder
    public Market(String email, String title, String content, String category, String state, String delivery, int price, int quantity, LocalDateTime createDate) {
        this.email = email;
        this.title = title;
        this.content = content;
        this.category = category;
        this.state = state;
        this.delivery = delivery;
        this.price = price;
        this.quantity = quantity;
        this.createDate = createDate;
    }

//    public Market update(String title, String content, String hashtags, LocalDateTime modifiedDate ){
//        this.title = title;
//        this.content = content;
//        this.hashtags = hashtags;
//        this.modifiedDate = modifiedDate;
//        return this;
//    }

//    public Market update(int readCount){
//        this.readCount = readCount;
//        return this;
//    }
}