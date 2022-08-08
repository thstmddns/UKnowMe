package com.ssafy.uknowme.web.service.keyword;

import com.ssafy.uknowme.model.dto.keywordDto.KeywordResponseDto;
import com.ssafy.uknowme.model.dto.keywordDto.KeywordSaveRequestDto;
import com.ssafy.uknowme.model.dto.keywordDto.KeywordUpdateRequestDto;
import com.ssafy.uknowme.web.domain.Keyword;
import com.ssafy.uknowme.web.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeywordServiceImpl {

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

    public KeywordResponseDto findByKeywordSeq(int keywordSeq) {
        Keyword entity = keywordRepository.findBySeq(keywordSeq).orElseThrow(() -> new IllegalArgumentException("해당 키워드가 존재하지 않습니다."));
        return new KeywordResponseDto(entity);
    }

    @Transactional
    public void delete (int keywordSeq) {
        Keyword keyword = keywordRepository.findBySeq(keywordSeq).orElseThrow(() -> new IllegalArgumentException("해당 키워드가 존재하지 않습니다."));
        keywordRepository.delete(keyword);
    }

    @Transactional(readOnly = true)
    public List<Keyword> findAll() {
        return keywordRepository.findAll();
    }
}
