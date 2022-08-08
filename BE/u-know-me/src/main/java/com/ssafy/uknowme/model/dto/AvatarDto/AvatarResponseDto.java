package com.ssafy.uknowme.model.dto.AvatarDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AvatarResponseDto {

    private int seq;

    private String name;

    private int frequency;
}
