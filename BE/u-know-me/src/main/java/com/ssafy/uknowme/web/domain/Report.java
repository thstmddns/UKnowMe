package com.ssafy.uknowme.web.domain;

import com.ssafy.uknowme.web.domain.common.BaseEntity;
import com.ssafy.uknowme.web.domain.enums.ReportState;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "report_seq")
    private int seq;

    /**
     * 신고자 정보
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporting_member_seq")
    private Member reportingMember;

    /**
     * 피신고자 정보
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accused_member_seq")
    private Member accusedMember;

    /**
     * 신고 상태
     * REPORT : 현재 신고 활성화
     * CANCEL : 관리자에 의해 신고가 취소됨
     */
    @Enumerated(EnumType.STRING)
    private ReportState state;

    @Builder
    public Report(int seq, Member reportingMember, Member accusedMember, ReportState state) {
        this.seq = seq;
        this.reportingMember = reportingMember;
        this.accusedMember = accusedMember;
        this.state = state;
    }
}
