package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.MemberRequestDto;
import com.ssafy.uknowme.model.dto.MemberResponseDto;
import com.ssafy.uknowme.model.dto.MemberUpdateDto;
import com.ssafy.uknowme.web.domain.Member;

public interface MemberService {

    boolean join(MemberRequestDto dto);

    MemberResponseDto login(MemberRequestDto dto);

    String update(MemberUpdateDto memberUpdateDto);

    boolean existsById(String memberId);

    boolean existsByNickname(String memberNickname);

    boolean existsByTel(String memberTel);
}
