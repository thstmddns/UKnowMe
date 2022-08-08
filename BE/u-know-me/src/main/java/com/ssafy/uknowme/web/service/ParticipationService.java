package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.ParticipationDto.ParticipationInfoResponseDto;
import com.ssafy.uknowme.model.dto.ParticipationDto.ParticipationSaveRequestDto;

public interface ParticipationService {

    boolean save(ParticipationSaveRequestDto dto);

    ParticipationInfoResponseDto findById(int participationSeq);

    boolean like(int memberSeq);
}
