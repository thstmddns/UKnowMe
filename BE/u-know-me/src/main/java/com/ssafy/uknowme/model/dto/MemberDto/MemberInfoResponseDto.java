package com.ssafy.uknowme.model.dto.MemberDto;


import com.ssafy.uknowme.model.dto.AvatarDto.AvatarResponseDto;
import com.ssafy.uknowme.web.domain.Member;
import com.ssafy.uknowme.web.domain.enums.ReportState;
import com.ssafy.uknowme.web.domain.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberInfoResponseDto {
    private int seq;
    private String id;

    private AvatarResponseDto avatar;
    private String name;
    private String nickname;
    private String gender;
    private String birth;
    private String tel;
    private String smoke;
    private String address;

    private Role role;
    private ReportState reportState;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public void convertFromEntity(Member member) {
        this.seq = member.getSeq();
        this.id = member.getId();
        this.avatar = AvatarResponseDto.convertFromEntity(member.getAvatar());
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.gender = member.getGender();
        this.birth = member.getBirth();
        this.tel = member.getTel();
        this.smoke = member.getSmoke();
        this.address = member.getAddress();
        this.role = member.getRole();
        this.reportState = member.getReportState();
        this.createDate = member.getCreateDate();
        this.updateDate = member.getUpdateDate();
    }
}
