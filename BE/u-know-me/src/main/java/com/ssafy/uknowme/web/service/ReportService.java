package com.ssafy.uknowme.web.service;


import com.ssafy.uknowme.model.dto.MemberDto.MemberUpdateDto;
import com.ssafy.uknowme.model.dto.ReportDto.*;
import com.ssafy.uknowme.web.domain.Report;

import java.util.List;

public interface ReportService {
    boolean report(ReportRequestDto dto);
    boolean update(ReportUpdateRequestDto dto);

    boolean delete(ReportDeleteRequestDto dto);

    List<ReportResponseDto> getReportInfos(ReportInfosRequestDto dto);
    ReportResponseDto getReportInfo(ReportInfoRequestDto dto);
}
