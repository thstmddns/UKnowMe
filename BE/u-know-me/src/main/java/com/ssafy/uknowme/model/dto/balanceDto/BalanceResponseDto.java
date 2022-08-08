package com.ssafy.uknowme.model.dto.balanceDto;

import com.ssafy.uknowme.web.domain.Balance;
import lombok.Getter;

@Getter
public class BalanceResponseDto {

    private String question;
    private String answer1;
    private String answer2;

    public BalanceResponseDto(Balance entity) {
        this.question = entity.getQuestion();
        this.answer1 = entity.getAnswer1();
        this.answer2 = entity.getAnswer2();
    }
}
