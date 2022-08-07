package com.ssafy.uknowme.web.domain;

import com.ssafy.uknowme.model.dto.MemberDto.MemberUpdateDto;
import com.ssafy.uknowme.web.domain.common.BaseEntity;
import com.ssafy.uknowme.web.domain.enums.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = @Index(columnList = "id", unique = true))
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_seq")
    private int seq;

    /**
     * 착용 아바타
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avatar_seq")
    private Avatar avatar;

    /**
     * 로그인할 ID
     */
    @Column(unique = true, nullable = false)
    private String id;

    /**
     * 로그인할 패스워드
     */
    private String password;

    /**
     * 사용자 이름
     */
    private String name;

    /**
     * 사용자 닉네임
     */
    private String nickname;

    /**
     * 성별
     * M : 남자
     * F : 여자
     */
    private String gender;

    /**
     * 생년월일
     * YYMMDD
     */
    private String birth;

    /**
     * 전화번호
     */
    private String tel;

    /**
     * 흡연 여부
     * Y : 흡연
     * N : 비흡연
     */
    private String smoke;

    /**
     * 주소
     */
    private String address;

    /**
     * 권한
     * USER : 일반 유저
     * MANAGER : 관리자
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * 네이버 소셜 로그인 식별자
     */
    private String naverId;

    /**
     * 카카오 소셜 로그인 식별자
     */
    private String kakaoId;

    /**
     * 매칭된 횟수
     */
    private int matchCount;

    @Builder
    public Member(int seq, String id, String password, String name, String nickname,
                  String gender, String birth, String tel, String smoke, String address,
                  Role role, String naverId, String kakaoId, int matchCount) {
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
        this.role = role;
        this.naverId = naverId;
        this.kakaoId = kakaoId;
        this.matchCount = matchCount;
    }

    public void updateMember(MemberUpdateDto dto) {
        this.smoke = dto.getSmoke();
        this.address = dto.getAddress();
    }
}
