package com.ssafy.uknowme.model.dto.balanceDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BalanceSaveRequestDto {
    private String question;
    private String answer1;
    private String answer2;
}
