package com.ssafy.uknowme.web.domain;

import com.ssafy.uknowme.web.domain.enums.ReportState;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Report {

    @Id @GeneratedValue
    @Column(name = "report_seq")
    private int seq;

    @ManyToOne
    @JoinColumn(name = "member_seq")
    private Member reportingMember;

    @ManyToOne
    @JoinColumn(name = "member_seq")
    private Member accusedMember;

    private ReportState state;
}
