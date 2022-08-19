package com.ssafy.uknowme.model.dto;

import com.ssafy.uknowme.web.domain.Keyword;
import lombok.Getter;

@Getter
public class KeywordResponseDto {

    private int seq;
    private String keyword;

    public KeywordResponseDto(Keyword entity) {
        this.seq = entity.getSeq();
        this.keyword = entity.getKeyword();
    }
}
