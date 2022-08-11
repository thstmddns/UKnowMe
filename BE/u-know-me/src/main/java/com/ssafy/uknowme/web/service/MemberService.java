package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.MemberDto.*;

import java.util.List;

public interface MemberService {

    boolean join(MemberJoinRequestDto dto);

    boolean update(MemberUpdateDto memberUpdateDto);

    boolean existsById(String memberId);

    boolean existsByNickname(String memberNickname);

    boolean existsByTel(String memberTel);

    boolean delete();

    MemberInfoResponseDto getMemberInfo();

    boolean validatePassword(ValidatePasswordRequestDto dto);

    FindIdResponseDto findId(FindIdRequestDto dto);

    List<ManageMemberInfoResponseDto> getMemberList();

    ManageMemberInfoResponseDto getMemberBySeq(int seq);

    void changeAvatar(ChangeAvatarDto dto);

    void changePassword(ChangePasswordDto dto);
}
