package com.ssafy.uknowme.web.domain;

import com.ssafy.uknowme.web.domain.enums.AvatarState;

import javax.persistence.*;

@Entity
public class Avatar {

    @Id @GeneratedValue
    @Column(name = "avatar_seq")
    private int seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_seq")
    private Member member;

    private String name;

    private String file;

    private int frequency;

    private AvatarState state;
}
