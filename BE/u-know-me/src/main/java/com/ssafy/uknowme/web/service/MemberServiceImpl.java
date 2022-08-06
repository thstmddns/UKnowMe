package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.MemberRequestDto;
import com.ssafy.uknowme.model.dto.MemberUpdateDto;
import com.ssafy.uknowme.web.domain.Member;
import com.ssafy.uknowme.web.domain.enums.Role;
import com.ssafy.uknowme.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
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


        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        Member member = Member.builder()
                .id(dto.getId())
                .password(encoder.encode(dto.getPassword()))
                .name(dto.getName())
                .nickname(dto.getNickname())
                .gender(dto.getBirth())
                .birth(dto.getBirth())
                .tel(dto.getTel())
                .smoke(dto.getSmoke())
                .address(dto.getAddress())
                .role(Role.USER)
                .build();

        repository.save(member);

        return true;
    }


    @Override
    public boolean update(MemberUpdateDto memberUpdateDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 로그인한 회원이 아니면 수정을 허용하지 말아야 한다.
        if (authentication == null) {
            log.info("로그인한 회원이 아닙니다.");
            return false;
        }

        // 본인이 아니면 수정을 허용하지 말아야 한다.
        if (!authentication.getName().equals(memberUpdateDto.getId())) {
            log.info("본인만 정보를 변경할 수 있습니다.");
            return false;
        }

        Member member = repository.findById(memberUpdateDto.getId()).orElseThrow(() -> new IllegalStateException("해당 아이디가 없습니다."));

        member.updateMember(memberUpdateDto.getName(), memberUpdateDto.getNickname(),
                memberUpdateDto.getTel(), memberUpdateDto.getSmoke(), memberUpdateDto.getAddress(),
                memberUpdateDto.getNaverId(), memberUpdateDto.getKakaoId());

        return true;
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

    @Override
    public boolean delete() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 로그인한 회원이 아니면 수정을 허용하지 말아야 한다.
        if (authentication == null) {
            log.info("로그인한 회원이 아닙니다.");
            return false;
        }

        String id = authentication.getName();

        Member member = repository.findById(id).orElseThrow(() -> new IllegalStateException("해당 아이디가 없습니다."));

        member.delete();

        return true;
    }
}

