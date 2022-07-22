package com.ssafy.uknowme.web.domain;

import com.ssafy.uknowme.web.domain.enums.BlockState;

import javax.persistence.*;

@Entity
public class Block {

    @Id
    @GeneratedValue
    @Column(name = "block_seq")
    private int seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocking_member_seq")
    private Member blockingMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocked_member_seq")
    private Member blockedMember;

    @Enumerated(EnumType.STRING)
    private BlockState state;
}
