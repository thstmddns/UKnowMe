package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.KeywordListResponseDto;
import com.ssafy.uknowme.model.dto.KeywordResponseDto;
import com.ssafy.uknowme.model.dto.KeywordSaveRequestDto;
import com.ssafy.uknowme.model.dto.KeywordUpdateRequestDto;
import com.ssafy.uknowme.web.domain.Keyword;
import com.ssafy.uknowme.web.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeywordServiceImpl implements KeywordService {

    private final KeywordRepository keywordRepository;

    @Transactional
    public Integer save(KeywordSaveRequestDto requestDto) {
        Keyword keyword = toEntity(requestDto);
        keywordRepository.save(keyword);
        return keyword.getSeq();
    }

    public Keyword toEntity(KeywordSaveRequestDto dto) {
        return Keyword.builder()
                .keyword(dto.getKeyword())
                .build();
    }

    @Transactional
    public Integer update(int keywordSeq, KeywordUpdateRequestDto requestDto) {
        Keyword keyword = keywordRepository.findBySeq(keywordSeq).orElseThrow(() -> new IllegalArgumentException("해당 키워드가 존재하지 않습니다."));

        keyword.update(requestDto.getKeyword());

        return keywordSeq;
    }

    @Transactional
    public KeywordResponseDto findByKeywordSeq() {

        List<KeywordResponseDto> dtoList = keywordRepository.findAll().stream()
                .map(KeywordResponseDto::new)
                .collect(Collectors.toList());

        int rKeywordSeq = (int) (Math.random() * dtoList.size());

        return dtoList.get(rKeywordSeq);
    }

    @Transactional
    public List<KeywordListResponseDto> findAll() {
        return keywordRepository.findAll().stream()
                .map(KeywordListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete (int keywordSeq) {
        Keyword keyword = keywordRepository.findBySeq(keywordSeq).orElseThrow(() -> new IllegalArgumentException("해당 키워드가 존재하지 않습니다."));
        keywordRepository.delete(keyword);
    }
}
