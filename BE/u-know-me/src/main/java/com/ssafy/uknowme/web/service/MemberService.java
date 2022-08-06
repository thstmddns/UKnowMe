package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.FindIdRequestDto;
import com.ssafy.uknowme.model.dto.FindIdResponseDto;
import com.ssafy.uknowme.model.dto.MemberDto.MemberInfoResponseDto;
import com.ssafy.uknowme.model.dto.MemberDto.MemberJoinRequestDto;
import com.ssafy.uknowme.model.dto.MemberDto.MemberUpdateDto;
import com.ssafy.uknowme.model.dto.MemberDto.ValidatePasswordRequestDto;

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
}
