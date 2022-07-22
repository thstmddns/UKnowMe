package com.ssafy.uknowme.web.domain;

import com.ssafy.uknowme.web.domain.enums.ReportState;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Report {

    @Id
    @GeneratedValue
    @Column(name = "report_seq")
    private int seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporting_member_seq")
    private Member reportingMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accused_member_seq")
    private Member accusedMember;

    @Enumerated(EnumType.STRING)
    private ReportState state;
}
