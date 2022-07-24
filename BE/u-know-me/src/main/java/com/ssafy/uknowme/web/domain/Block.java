package com.ssafy.uknowme.web.domain;

import com.ssafy.uknowme.web.domain.common.BaseEntity;
import com.ssafy.uknowme.web.domain.enums.BlockState;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Block extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "block_seq")
    private int seq;

    /**
     * 차단한 유저
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocking_member_seq")
    private Member blockingMember;

    /**
     * 차단된 유저
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocked_member_seq")
    private Member blockedMember;

    /**
     * 차단 상태
     * BLOCK : 차단된 상태
     * CANCEL : 차단이 취소된 상태
     */
    @Enumerated(EnumType.STRING)
    private BlockState state;

    @Builder
    public Block(int seq, Member blockingMember, Member blockedMember, BlockState state) {
        this.seq = seq;
        this.blockingMember = blockingMember;
        this.blockedMember = blockedMember;
        this.state = state;
    }
}
