package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.MemberRequestDto;
import com.ssafy.uknowme.model.dto.MemberResponseDto;
import com.ssafy.uknowme.model.dto.MemberUpdateDto;
import com.ssafy.uknowme.web.domain.Member;
import com.ssafy.uknowme.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository repository;


    @Override
    public boolean join(MemberRequestDto dto) {

        if (existsById(dto.getId())) {
            return false;
        } if (existsByNickname(dto.getNickname())) {
            return false;
        } if (existsByTel(dto.getTel())) {
            return false;
        }


        Member member = Member.builder()
                .id(dto.getId())
                .password(dto.getPassword())
                .name(dto.getName())
                .nickname(dto.getNickname())
                .gender(dto.getBirth())
                .birth(dto.getBirth())
                .tel(dto.getTel())
                .smoke(dto.getSmoke())
                .address(dto.getAddress())
                .build();



        repository.save(member);

        return true;
    }


    @Override
    @Transactional(readOnly = true)
    public MemberResponseDto login(MemberRequestDto dto) {

        Member findMember = repository.findByIdAndPassword(dto.getId(), dto.getPassword());

        MemberResponseDto responseDto = new MemberResponseDto();
        responseDto.setSeq(findMember.getSeq());

        return responseDto;
    }



    @Override
    public String update(MemberUpdateDto memberUpdateDto) {
        Member member = findById(memberUpdateDto);

        member.updateMember(memberUpdateDto.getName(), memberUpdateDto.getNickname(),
                memberUpdateDto.getTel(), memberUpdateDto.getSmoke(), memberUpdateDto.getAddress(),
                memberUpdateDto.getNaverId(), memberUpdateDto.getKakaoId());

        return null;
    }

    @Override
    public boolean existsById(String memberId) {
        return repository.existsById(memberId);
    }

    @Override
    public boolean existsByNickname(String memberNickname) {
        return repository.existsByNickname(memberNickname);
    }

    @Override
    public boolean existsByTel(String memberTel) {
        return repository.existsByTel(memberTel);
    }

    private Member findById(MemberUpdateDto memberUpdateDto) {
        Member member;
        try {
            member = repository.findById(memberUpdateDto.getId()).orElseThrow(() -> new IllegalAccessException("해당 아이디가 없습니다."));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return member;
    }
}

