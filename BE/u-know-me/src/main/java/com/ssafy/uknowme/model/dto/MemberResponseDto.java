package com.ssafy.uknowme.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponseDto {
    private int seq;
    private String id;
    private String password;
    private String name;
    private String nickname;
    private String gender;
    private String birth;
    private String tel;
    private String smoke;
    private String address;
}
