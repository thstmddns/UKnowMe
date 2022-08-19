package com.ssafy.uknowme.model.dto.BlockDto;

import com.ssafy.uknowme.web.domain.enums.BlockState;
import com.ssafy.uknowme.web.domain.enums.ReportState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlockRequestDto {


    int blokingMemberSeq;
    int blockedMemberSeq;
    BlockState state;

}
