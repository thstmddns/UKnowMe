package com.ssafy.uknowme.model.dto.ReportDto;

import com.ssafy.uknowme.model.dto.MemberDto.MemberInfoResponseDto;
import com.ssafy.uknowme.web.domain.Report;
import com.ssafy.uknowme.web.domain.enums.DeleteState;
import com.ssafy.uknowme.web.domain.enums.ReportState;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponseDto {

    private int reportSeq;

    private MemberInfoResponseDto reportingMember;

    private MemberInfoResponseDto accusedMember;

    private ReportState state;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private String createMember;

    private String updateMember;

    private DeleteState deleteYn;


    //TODO(명범): memberInfoResponseDto 받아오도록 리팩토링 해야합니다🚑

    public void convertToEntity(Report report) {
        MemberInfoResponseDto reportingMemberDto = new MemberInfoResponseDto();
        MemberInfoResponseDto accusedMemberDto = new MemberInfoResponseDto();

        /**
         * DTO convertToEntity() 사용해서 데이터 주입하기
         */

        this.reportSeq = report.getSeq();
        this.reportingMember = reportingMemberDto;
        this.accusedMember = accusedMemberDto;
        this.state = report.getState();
        this.createDate = report.getCreateDate();
        this.updateDate = report.getUpdateDate();
        this.createMember = report.getCreateMember();
        this.updateMember = report.getUpdateMember();
        this.deleteYn = report.getDeleteYn();
    }

}
