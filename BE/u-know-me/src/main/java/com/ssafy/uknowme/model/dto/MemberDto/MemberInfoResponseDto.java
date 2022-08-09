package com.ssafy.uknowme.model.dto.MemberDto;


import com.ssafy.uknowme.web.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInfoResponseDto {
    private int seq;
    private String id;
    private String name;
    private String nickname;
    private String gender;
    private String birth;
    private String tel;
    private String smoke;
    private String address;

    public void convertFromEntity(Member member) {
        this.seq = member.getSeq();
        this.id = member.getId();
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.gender = member.getGender();
        this.birth = member.getBirth();
        this.tel = member.getTel();
        this.smoke = member.getSmoke();
        this.address = member.getAddress();
    }
}
