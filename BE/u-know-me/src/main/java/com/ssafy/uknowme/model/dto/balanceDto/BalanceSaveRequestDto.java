package com.ssafy.uknowme.model.dto.balanceDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BalanceSaveRequestDto {
    private String question;
    private String answer1;
    private String answer2;
}
