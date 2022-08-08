package com.ssafy.uknowme.model.dto.keywordDto;


import com.ssafy.uknowme.web.domain.Keyword;
import lombok.Getter;

@Getter
public class KeywordListResponseDto {
    private String keyword;

    public KeywordListResponseDto(Keyword entity) {
        this.keyword = entity.getKeyword();
    }
}
