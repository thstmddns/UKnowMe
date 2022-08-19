package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.MemberDto.*;

import java.util.List;

public interface MemberService {

    boolean join(MemberJoinRequestDto dto);

    boolean update(MemberUpdateDto memberUpdateDto, String loginId);

    boolean existsById(String memberId);

    boolean existsByNickname(String memberNickname);

    boolean existsByTel(String memberTel);

    boolean delete(String id);

    MemberInfoResponseDto getMemberInfo(String loginId);

    boolean validatePassword(ValidatePasswordRequestDto dto, String loginId);

    FindIdResponseDto findId(FindIdRequestDto dto);

    List<ManageMemberInfoResponseDto> getMemberList();

    ManageMemberInfoResponseDto getMemberBySeq(int seq);

    void changeAvatar(ChangeAvatarDto dto, String loginId);

    void changePassword(ChangePasswordDto dto);
}
