package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.MemberDto.*;
import com.ssafy.uknowme.web.domain.Avatar;
import com.ssafy.uknowme.web.domain.Member;
import com.ssafy.uknowme.web.domain.enums.Role;
import com.ssafy.uknowme.web.repository.AvatarRepository;
import com.ssafy.uknowme.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository repository;

    private final AvatarRepository avatarRepository;


    @Override
    public boolean join(MemberJoinRequestDto dto) {
        if (existsById(dto.getId())) {
            return false;
        } if (existsByNickname(dto.getNickname())) {
            return false;
        } if (existsByTel(dto.getTel())) {
            return false;
        }


        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        Avatar avatar = initAvatar(dto);

        Member member = Member.builder()
                .id(dto.getId())
                .avatar(avatar)
                .password(encoder.encode(dto.getPassword()))
                .name(dto.getName())
                .nickname(dto.getNickname())
                .gender(dto.getGender())
                .birth(dto.getBirth())
                .tel(dto.getTel())
                .smoke(dto.getSmoke())
                .address(dto.getAddress())
                .role(Role.ROLE_USER)
                .build();

        repository.save(member);

        return true;
    }

    private Avatar initAvatar(MemberJoinRequestDto dto) {
        if (dto.getGender().equals("M")) {
            return avatarRepository.findById(4).orElseThrow(IllegalStateException::new);
        } else {
            return avatarRepository.findById(1).orElseThrow(IllegalAccessError::new);
        }
    }

    @Override
    public boolean update(MemberUpdateDto memberUpdateDto, String loginId) {
        Member member = repository.findById(loginId).orElseThrow(() -> new IllegalStateException("해당 아이디가 없습니다."));

        member.updateMember(memberUpdateDto);

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
    public boolean delete(String id) {
        Member member = repository.findById(id).orElseThrow(() -> new IllegalStateException("해당 아이디가 없습니다."));

        member.delete();

        return true;
    }

    @Override
    public MemberInfoResponseDto getMemberInfo(String loginId) {

        Member member = repository.findById(loginId).orElseThrow(() -> new IllegalStateException("해당 아이디가 없습니다."));

        MemberInfoResponseDto responseDto = new MemberInfoResponseDto();

        responseDto.convertFromEntity(member);

        return responseDto;
    }

    @Override
    public boolean validatePassword(ValidatePasswordRequestDto dto, String loginId) {

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        Member member = repository.findById(loginId).orElseThrow(() -> new IllegalStateException("해당 아이디가 없습니다."));

        if (!encoder.matches(dto.getPassword(), member.getPassword())) {
            log.info("비밀번호가 다릅니다.");
            return false;
        }

        return true;
    }

    @Override
    public FindIdResponseDto findId(FindIdRequestDto requestDto) {

        Optional<Member> optionalMember = repository.findByNameAndTel(requestDto.getName(), requestDto.getTel());

        if (!optionalMember.isPresent()) return null;

        Member member = optionalMember.get();

        FindIdResponseDto responseDto = new FindIdResponseDto();

        responseDto.setId(member.getId());

        return responseDto;
    }

    @Override
    public List<ManageMemberInfoResponseDto> getMemberList() {
        return repository.findManageMemberInfoResponseDtoList(LocalDateTime.now().minusDays(7));
    }

    @Override
    public ManageMemberInfoResponseDto getMemberBySeq(int seq) {
        return repository.findManageMemberInfoResponseDto(LocalDateTime.now().minusDays(7), seq).orElseThrow(IllegalStateException::new);
    }

    @Override
    public void changeAvatar(ChangeAvatarDto dto, String loginId) {
        Member member = repository.findById(loginId).orElseThrow(IllegalStateException::new);

        Avatar avatar = avatarRepository.findById(dto.getAvatarSeq()).orElseThrow(IllegalStateException::new);

        member.changeAvatar(avatar);
    }

    @Override
    public void changePassword(ChangePasswordDto dto) {
        Member member = repository.findById(dto.getId()).orElseThrow(IllegalStateException::new);

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        member.changePassword(encoder.encode(dto.getChangePassword()));
    }
}

