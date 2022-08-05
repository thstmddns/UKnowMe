package com.ssafy.uknowme.model.dto.MatchingDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RoomRequestDto {

    private String nickname;
    private String gender;
    private String birth;
    private String smoke;

}
