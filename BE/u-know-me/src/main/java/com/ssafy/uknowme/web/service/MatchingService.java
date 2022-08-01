package com.ssafy.uknowme.web.service;


import com.ssafy.uknowme.model.dto.MatchingRequestDto;
import com.ssafy.uknowme.model.dto.MatchingResponseDto;

public interface MatchingService {
    MatchingResponseDto getMatchingMemberInfo(MatchingRequestDto dto) throws Exception;
}