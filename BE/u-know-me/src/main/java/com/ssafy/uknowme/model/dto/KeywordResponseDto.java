package com.ssafy.uknowme.model.dto.keywordDto;

import com.ssafy.uknowme.web.domain.Keyword;
import lombok.Getter;

@Getter
public class KeywordResponseDto {

    private String keyword;

    public KeywordResponseDto(Keyword entity) {
        this.keyword = entity.getKeyword();
    }
}
