package com.ssafy.uknowme.model.dto.ReportDto;

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

    int reportseq;
    int reportingMemberSeq;
    int accusedMemberSeq;

    String reportingMemberId;
    String accusedMemberId;

    String reportingMemberNickname;
    String accusedMemberNickname;

    ReportState state;

    LocalDateTime createDate;

    LocalDateTime updateDate;

    private String createMember;

    private String updateMember;

    private DeleteState deleteYn;

}
