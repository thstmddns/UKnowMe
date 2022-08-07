package com.ssafy.uknowme.model.dto.ReportDto;

import com.ssafy.uknowme.web.domain.Member;
import com.ssafy.uknowme.web.domain.enums.ReportState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportRequestDto {

    int reportingMemberSeq;
    int accusedMemberSeq;
    ReportState reportState;

}
