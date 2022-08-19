package com.ssafy.uknowme.web.controller;

import com.ssafy.uknowme.model.dto.MemberDto.*;
import com.ssafy.uknowme.security.annotation.AuthenticationId;
import com.ssafy.uknowme.web.exception.BadRequestException;
import com.ssafy.uknowme.web.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value="회원 가입 API", notes="사용자가 회원 가입을 할 때 이용되는 API입니다.")
    @PostMapping("/join")
    public ResponseEntity<?> join(@Validated @RequestBody MemberJoinRequestDto dto) {
        if (memberService.join(dto)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value="아이디 중복 검사 API", notes="사용자가 회원 가입 시 아이디 중복 검사에 이용하는 API입니다.")
    @GetMapping("/check/id")
    public ResponseEntity<?> checkIdDuplication(@ModelAttribute DuplicatedIdRequestDto dto) throws BadRequestException {
        if (memberService.existsById(dto.getId())) {
            return new ResponseEntity<>("false", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("true", HttpStatus.OK);
        }
    }

    @ApiOperation(value="닉네임 중복 검사 API", notes="사용자가 회원 가입 시 닉네임 중복 검사에 이용하는 API입니다.")
    @GetMapping("/check/nickname")
    public ResponseEntity<?> checkNickNameDuplication(@ModelAttribute DuplicatedNicknameRequestDto dto) throws BadRequestException {
        if (memberService.existsByNickname(dto.getNickname())) {
            return new ResponseEntity<>("false", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("true", HttpStatus.OK);
        }
    }

    @ApiOperation(value="멤버 정보 수정 API", notes="사용자가 자신의 계정 정보를 수정하고자 할 때 사용하는 API입니다.")
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody MemberUpdateDto dto, @AuthenticationId String loginId) {
        if (memberService.update(dto, loginId)) {
            return new ResponseEntity<>("true", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("false", HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value="아바타 변경 API", notes="사용자가 착용 아바타를 변경할 때 사용하는 API입니다.")
    @PutMapping("/avatar")
    public ResponseEntity<?> changeAvatar(@RequestBody ChangeAvatarDto dto, @AuthenticationId String loginId) {
        try {
            memberService.changeAvatar(dto, loginId);
            return new ResponseEntity<>("true", HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>("false", HttpStatus.OK);
        }
    }

    @ApiOperation(value="비밀번호 변경 API", notes="전화번호 인증 후 비밀번호를 변경할 때 사용하는 API입니다.")
    @PutMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto dto) {
        try {
            memberService.changePassword(dto);
            return new ResponseEntity<>("true", HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>("false", HttpStatus.OK);

        }
    }

    @ApiOperation(value="멤버 정보 조회 API", notes="사용자 정보를 조회할 때 사용하는 API입니다. 로그인해야 사용할 수 있습니다.")
    @GetMapping
    public ResponseEntity<?> getMemberInfo(@AuthenticationId String loginId) {
        return new ResponseEntity<>(memberService.getMemberInfo(loginId), HttpStatus.OK);
    }

    @ApiOperation(value="멤버 전체 리스트 조회 API", notes="관리자가 멤버 전체 리스트를 조회할 때 사용하는 API입니다. 로그인해야 사용할 수 있습니다. 관리자만 사용할 수 있습니다.")
    @GetMapping("/list")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> getMemberList() {
        for (ManageMemberInfoResponseDto dto : memberService.getMemberList()) {
            System.out.println(dto);
        }
        return new ResponseEntity<>(memberService.getMemberList(), HttpStatus.OK);
    }

    @GetMapping("/{memberSeq}")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> getMemberBySeq(@PathVariable int memberSeq) {
        try {
            return new ResponseEntity<>(memberService.getMemberBySeq(memberSeq), HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @ApiOperation(value="비밀번호 검증 API", notes="비밀번호를 검증할 때 사용하는 API입니다. 로그인해야 사용할 수 있습니다.")
    @PostMapping("/validate/password")
    public ResponseEntity<?> validatePassword(@RequestBody ValidatePasswordRequestDto dto, @AuthenticationId String loginId) {
        return new ResponseEntity<>(memberService.validatePassword(dto, loginId), HttpStatus.OK);
    }

    @ApiOperation(value="회원 탈퇴 API", notes="사용자가 본인의 계정을 삭제하고 싶을 때 사용하는 API입니다.")
    @DeleteMapping
    public ResponseEntity<?> delete(@AuthenticationId String memberId) {

        if (memberService.delete(memberId)) {
            return new ResponseEntity<>("true", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("false", HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value="아이디 찾기 API", notes="사용자가 본인의 이름과 전화번호를 이용해 아이디를 찾을 수 있는 API입니다.")
    @GetMapping("/find/id")
    public ResponseEntity<?> findId(@ModelAttribute FindIdRequestDto requestDto) {
        FindIdResponseDto responseDto = memberService.findId(requestDto);

        if (responseDto == null) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }

    }
}
