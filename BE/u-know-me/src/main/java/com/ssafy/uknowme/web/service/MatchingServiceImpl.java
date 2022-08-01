package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.MatchingRequestDto;
import com.ssafy.uknowme.model.dto.MatchingResponseDto;
import com.ssafy.uknowme.web.domain.Member;
import com.ssafy.uknowme.web.repository.MatchingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class  MatchingServiceImpl implements  MatchingService{


    private final MatchingRepository repository;
    @Override
    public MatchingResponseDto getMatchingMemberInfo(MatchingRequestDto dto) throws Exception {
        log.info("id는 {}", dto.getId());
        Member member = repository.findById(dto.getId());

        MatchingResponseDto matchingResponseDto = new MatchingResponseDto();

        matchingResponseDto.setBirth(member.getBirth());
        matchingResponseDto.setBirth(member.getBirth());
        matchingResponseDto.setBirth(member.getBirth());
        matchingResponseDto.setBirth(member.getBirth());

        System.out.println(matchingResponseDto.getBirth());


        return matchingResponseDto;
    }
}
