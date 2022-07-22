package com.ssafy.uknowme.web.domain;

import jdk.vm.ci.meta.Local;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Participation {

    @Id @GeneratedValue
    @Column(name = "participation_seq")
    private int seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_seq")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_seq")
    private Room room;

    private LocalDateTime likeDate;

    private LocalDateTime disconnectDate;
}
