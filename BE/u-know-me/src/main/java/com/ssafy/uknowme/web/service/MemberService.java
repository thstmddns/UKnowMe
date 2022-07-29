package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.MemberRequestDto;
import com.ssafy.uknowme.model.dto.MemberResponseDto;
import com.ssafy.uknowme.model.dto.MemberUpdateDto;
import com.ssafy.uknowme.web.domain.Member;

public interface MemberService {

    boolean join(MemberRequestDto dto);

    MemberResponseDto login(MemberRequestDto dto);

    String update(MemberUpdateDto memberUpdateDto);

    boolean existsByMemberId(String memberId);

    boolean existsByMemberNickName(String memberNickName);
}
