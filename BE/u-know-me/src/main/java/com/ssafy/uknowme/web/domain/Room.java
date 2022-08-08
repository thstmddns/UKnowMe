package com.ssafy.uknowme.web.domain;

import com.ssafy.uknowme.web.domain.common.BaseEntity;
import com.ssafy.uknowme.web.domain.enums.RoomType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends BaseEntity {

    @Id
    @Column(name = "room_seq")
    private String seq;

    /**
     * 밸런스 게임 실행 횟수
     */
    private int balanceCount;

    /**
     * 화상 채팅의 타입을 결정하는 필드
     * ONE : 1:1 화상 채팅
     * TWO : 2:2 화상 채팅
     */
    @Enumerated(EnumType.STRING)
    private RoomType type;

    @Builder
    public Room(String seq, int balanceCount, RoomType type) {
        this.seq = seq;
        this.balanceCount = balanceCount;
        this.type = type;
    }

    public void addBalanceCount() {
        this.balanceCount++;
    }
}
