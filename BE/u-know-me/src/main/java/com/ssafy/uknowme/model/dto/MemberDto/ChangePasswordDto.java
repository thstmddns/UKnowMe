package com.ssafy.uknowme.model.dto.MemberDto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter @Setter
public class ChangePasswordDto {

    private String id;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$")
    private String changePassword;
}
