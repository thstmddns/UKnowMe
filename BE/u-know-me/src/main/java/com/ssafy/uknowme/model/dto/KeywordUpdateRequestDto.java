package com.ssafy.uknowme.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KeywordUpdateRequestDto {

    private String keyword;

    @Builder
    public KeywordUpdateRequestDto(String keyword) {
        this.keyword = keyword;
    }
}
