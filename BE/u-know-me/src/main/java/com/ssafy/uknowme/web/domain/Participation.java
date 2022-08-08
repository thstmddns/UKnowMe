package com.ssafy.uknowme.web.domain;

import com.ssafy.uknowme.web.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participation extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "participation_seq")
    private int seq;

    /**
     * 화상 채팅 참가자 정보
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_seq")
    private Member member;

    /**
     * 화상 채팅 정보
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_seq")
    private Room room;

    /**
     * 해당 참가자가 하트를 누른 일자
     */
    private LocalDateTime likeDate;

    /**
     * 연결 종료 일자
     */
    private LocalDateTime disconnectDate;

    @Builder
    public Participation(int seq, Member member, Room room, LocalDateTime likeDate, LocalDateTime disconnectDate) {
        this.seq = seq;
        this.member = member;
        this.room = room;
        this.likeDate = likeDate;
        this.disconnectDate = disconnectDate;
    }

    public void like() {
        this.likeDate = LocalDateTime.now();
    }

    public void disconnect() {
        this.disconnectDate = LocalDateTime.now();
    }
}
