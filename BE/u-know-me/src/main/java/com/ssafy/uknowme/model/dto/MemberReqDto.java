package com.ssafy.uknowme.model.dto;

public class MemberReqDto {
    private int seq;
    private String id;
    private int password;
    private String name;
    private String nickname;
    private String gender;
    private String birth;
    private String tel;
    private String smoke;
    private String address;

    public MemberReqDto() {
        super();
    }

    public MemberReqDto(int seq, String id, int password, String name,
            String nickname,String gender, String birth, String tel, String smoke, String address) {
        super();
        this.seq = seq;
        this.id = id;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.gender = gender;
        this.birth = birth;
        this.tel = tel;
        this.smoke = smoke;
        this.address = address;
    }
    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }


}
