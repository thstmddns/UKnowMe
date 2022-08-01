package com.ssafy.uknowme.web.service;


import com.ssafy.uknowme.model.dto.MatchingResonseDto;

public interface MatchingService {
    MatchingResonseDto getMatchingMemberInfo(String id);
}