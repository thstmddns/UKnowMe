package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.balanceDto.BalanceListResponseDto;
import com.ssafy.uknowme.model.dto.balanceDto.BalanceResponseDto;
import com.ssafy.uknowme.model.dto.balanceDto.BalanceSaveRequestDto;
import com.ssafy.uknowme.model.dto.balanceDto.BalanceUpdateRequestDto;
import com.ssafy.uknowme.web.domain.Balance;

import java.util.List;

public interface BalanceService {

    String update(BalanceUpdateRequestDto balanceUpdateRequestDto);
    Integer save(BalanceSaveRequestDto requestDto);

    Balance toEntity(BalanceSaveRequestDto dto);

    Integer update(int balanceSeq, BalanceUpdateRequestDto requestDto);

    BalanceResponseDto findByBalanceSeq ();

    void delete (int balanceSeq);

    List<BalanceListResponseDto> findAll();
}
