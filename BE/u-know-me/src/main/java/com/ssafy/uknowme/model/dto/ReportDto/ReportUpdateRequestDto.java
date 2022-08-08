package com.ssafy.uknowme.model.dto.ReportDto;

import com.ssafy.uknowme.web.domain.enums.ReportState;
import com.ssafy.uknowme.web.domain.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportUpdateRequestDto {


    int memberSeq;
    int reportSeq;
    ReportState state;

}
