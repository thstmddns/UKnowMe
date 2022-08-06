package com.ssafy.uknowme.model.dto.MemberDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberUpdateDto {

    private String id;
    private String name;
    private String nickname;
    private String tel;
    private String smoke;
    private String address;
    private String naverId;
    private String kakaoId;
}
