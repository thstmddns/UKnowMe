package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.MatchingDto.MatchingRequestDto;
import com.ssafy.uknowme.model.dto.MatchingDto.MatchingResponseDto;
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
    public MatchingResponseDto get1vs1MatchingMemberInfo(MatchingRequestDto dto) throws Exception {

        Optional<Member> member = repository.findById(dto.getId());

        MatchingResponseDto matchingResponseDto = new MatchingResponseDto();

        if(member.isPresent()){
            matchingResponseDto = new MatchingResponseDto();

            matchingResponseDto.setBirth(member.get().getBirth());
            matchingResponseDto.setGender(member.get().getGender());
            matchingResponseDto.setNickname(member.get().getNickname());
            matchingResponseDto.setSmoke(member.get().getSmoke());
        }
        return matchingResponseDto;
    }

    @Override
    public MatchingResponseDto get2vs2MatchingMemberInfo(MatchingRequestDto dto) throws Exception {

        Optional<Member> member = repository.findById(dto.getId());

        MatchingResponseDto matchingResponseDto =  new MatchingResponseDto();

        if(member.isPresent()){
            matchingResponseDto = new MatchingResponseDto();

            matchingResponseDto.setBirth(member.get().getBirth());
            matchingResponseDto.setGender(member.get().getGender());
            matchingResponseDto.setNickname(member.get().getNickname());
        }
        return matchingResponseDto;
    }
}
