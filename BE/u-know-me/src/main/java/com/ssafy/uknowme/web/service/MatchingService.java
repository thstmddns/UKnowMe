package com.ssafy.uknowme.web.service;


import com.ssafy.uknowme.model.dto.MatchingDto.MatchingRequestDto;
import com.ssafy.uknowme.model.dto.MatchingDto.MatchingResponseDto;

public interface MatchingService {
    MatchingResponseDto get1vs1MatchingMemberInfo(MatchingRequestDto dto) throws Exception;
    MatchingResponseDto get2vs2MatchingMemberInfo(MatchingRequestDto dto) throws Exception;
}