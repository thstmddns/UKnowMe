package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.memberDto.MemberRequestDto;
import com.ssafy.uknowme.model.dto.memberDto.MemberResponseDto;
import com.ssafy.uknowme.model.dto.memberDto.MemberUpdateDto;

public interface MemberService {

    boolean join(MemberRequestDto dto);

    MemberResponseDto login(MemberRequestDto dto);

    boolean update(MemberUpdateDto memberUpdateDto);

    boolean existsById(String memberId);

    boolean existsByNickname(String memberNickname);

    boolean existsByTel(String memberTel);
}
