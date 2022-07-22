package com.ssafy.uknowme.web.domain;

import com.ssafy.uknowme.web.domain.enums.Role;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_seq")
    private int seq;

    private String id;

    private String password;

    private String name;

    private String gender;

    private String birth;

    private String tel;

    private String smoke;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String naverId;

    private String kakaoId;

    private int matchCount;
}
