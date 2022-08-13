package com.ssafy.uknowme.model.dto.balanceDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BalanceUpdateRequestDto {

    private String question;
    private String answer1;
    private String answer2;

    @Builder
    public BalanceUpdateRequestDto(String question, String answer1, String answer2) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
    }
}
