package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.KeywordListResponseDto;
import com.ssafy.uknowme.model.dto.KeywordResponseDto;
import com.ssafy.uknowme.model.dto.KeywordSaveRequestDto;
import com.ssafy.uknowme.model.dto.KeywordUpdateRequestDto;

import java.util.List;

public interface KeywordService {
    Integer save(KeywordSaveRequestDto requestDto);

    Integer update(int keywordSeq, KeywordUpdateRequestDto requestDto);

    KeywordResponseDto findByKeywordSeq();

    List<KeywordListResponseDto> findAll();

    void delete (int keywordSeq);
}
