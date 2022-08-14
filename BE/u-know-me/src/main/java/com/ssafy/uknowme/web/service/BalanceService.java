package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.balanceDto.BalanceListResponseDto;
import com.ssafy.uknowme.model.dto.balanceDto.BalanceResponseDto;
import com.ssafy.uknowme.model.dto.balanceDto.BalanceSaveRequestDto;
import com.ssafy.uknowme.model.dto.balanceDto.BalanceUpdateRequestDto;

import java.util.List;

public interface BalanceService {

    Integer save(BalanceSaveRequestDto requestDto);

    Integer update(int balanceSeq, BalanceUpdateRequestDto requestDto);

    BalanceResponseDto findByBalanceSeq ();

    void delete (int balanceSeq);

    List<BalanceListResponseDto> findAll();
}
