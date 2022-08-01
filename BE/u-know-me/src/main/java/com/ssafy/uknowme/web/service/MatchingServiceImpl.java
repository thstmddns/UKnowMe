package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.MatchingResonseDto;
import com.ssafy.uknowme.web.repository.MainPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class  MatchingServiceImpl implements  MatchingService{


    private final MainPageRepository repository;
    @Override
    public MatchingResonseDto getMatchingMemberInfo(String id) {

        MatchingResonseDto machingMemberDto = repository.findById(id);
        return machingMemberDto;
    }
}
