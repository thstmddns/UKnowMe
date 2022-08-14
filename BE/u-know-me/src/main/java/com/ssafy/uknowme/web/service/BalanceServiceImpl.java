package com.ssafy.uknowme.web.service;


import com.ssafy.uknowme.model.dto.balanceDto.BalanceListResponseDto;
import com.ssafy.uknowme.model.dto.balanceDto.BalanceResponseDto;
import com.ssafy.uknowme.model.dto.balanceDto.BalanceSaveRequestDto;
import com.ssafy.uknowme.model.dto.balanceDto.BalanceUpdateRequestDto;
import com.ssafy.uknowme.web.domain.Balance;
import com.ssafy.uknowme.web.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService{

    private final BalanceRepository balanceRepository;


    @Override
    @Transactional
    public Integer save(BalanceSaveRequestDto dto) {
        Balance balance = Balance.builder()
                            .question(dto.getQuestion())
                            .answer1(dto.getAnswer1())
                            .answer2(dto.getAnswer2())
                            .build();

        Balance result = balanceRepository.save(balance);

        return result.getSeq();
    }

    @Transactional
    public Integer update(int balanceSeq, BalanceUpdateRequestDto requestDto) {
        Balance balance = balanceRepository.findBySeq(balanceSeq).orElseThrow(() -> new IllegalArgumentException("해당 밸런스 게임이 존재하지 않습니다."));

        balance.update(requestDto.getQuestion(), requestDto.getAnswer1(), requestDto.getAnswer2());

        return balanceSeq;
    }

    @Transactional
    public BalanceResponseDto findByBalanceSeq () {

        List<BalanceResponseDto> dtoList = balanceRepository.findAll().stream()
                .map(BalanceResponseDto::new)
                .collect(Collectors.toList());

        int rBalanceSeq = (int) (Math.random() * dtoList.size());

        return dtoList.get(rBalanceSeq);
    }

    @Transactional
    public List<BalanceListResponseDto> findAll() {
        return balanceRepository.findAll().stream()
                .map(BalanceListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete (int balanceSeq) {
        Balance balance = balanceRepository.findBySeq(balanceSeq).orElseThrow(() -> new IllegalArgumentException("해당 밸런스 게임이 존재하지 않습니다."));
        balanceRepository.delete(balance);
    }
}
