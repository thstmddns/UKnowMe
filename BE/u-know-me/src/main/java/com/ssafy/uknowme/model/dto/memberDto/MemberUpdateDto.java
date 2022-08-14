package com.ssafy.uknowme.model.dto.MemberDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateDto {
    private String id;
    private String smoke;
    private String address;
}
