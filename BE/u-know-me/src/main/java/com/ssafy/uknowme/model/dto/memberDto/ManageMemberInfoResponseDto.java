package com.ssafy.uknowme.model.dto.MemberDto;


import com.ssafy.uknowme.web.domain.enums.ReportState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ManageMemberInfoResponseDto {
    private int seq;

    private String id;

    private String name;

    private String nickname;

    private String gender;

    private String birth;

    private String tel;

    private String smoke;

    private String address;

    private ReportState reportState;

    private Long accusedCount;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;
}
