package com.ssafy.uknowme.model.dto.keywordDto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KeywordUpdateRequestDto {

    private String keyword;

    @Builder
    public KeywordUpdateRequestDto(String keyword) {
        this.keyword = keyword;
    }
}
