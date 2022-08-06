package com.ssafy.uknowme.web.controller;

import com.ssafy.uknowme.model.dto.FindIdResponseDto;
import com.ssafy.uknowme.model.dto.MemberDto.*;
import com.ssafy.uknowme.model.dto.FindIdRequestDto;
import com.ssafy.uknowme.web.exception.BadRequestException;
import com.ssafy.uknowme.web.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> checkIdDuplication(@RequestBody DuplicatedIdRequestDto dto) throws BadRequestException {
        if (memberService.existsById(dto.getId())) {
            return new ResponseEntity<>("false", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("true", HttpStatus.OK);
        }
    }

    @ApiOperation(value="닉네임 중복 검사 API", notes="사용자가 회원 가입 시 닉네임 중복 검사에 이용하는 API입니다.")
    @GetMapping("/check/nickname")
    public ResponseEntity<?> checkNickNameDuplication(@RequestBody DuplicatedNicknameRequestDto dto) throws BadRequestException {
        if (memberService.existsByNickname(dto.getNickname())) {
            return new ResponseEntity<>("false", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("true", HttpStatus.OK);
        }
    }

    @ApiOperation(value="멤버 정보 수정 API", notes="사용자가 자신의 계정 정보를 수정하고자 할 때 사용하는 API입니다.")
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody MemberUpdateDto dto) {
        if (memberService.update(dto)) {
            return new ResponseEntity<>("true", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("false", HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value="멤버 정보 조회 API", notes="사용자 정보를 조회할 때 사용하는 API입니다. 로그인해야 사용할 수 있습니다.")
    @GetMapping
    public ResponseEntity<?> getMemberInfo() {
        return new ResponseEntity<>(memberService.getMemberInfo(), HttpStatus.OK);
    }

    @ApiOperation(value="비밀번호 검증 API", notes="비밀번호를 검증할 때 사용하는 API입니다. 마찬가지로 로그인해야 사용할 수 있습니다.")
    @GetMapping("/validate/password")
    public ResponseEntity<?> validatePassword(@RequestBody ValidatePasswordRequestDto dto) {
        return new ResponseEntity<>(memberService.validatePassword(dto), HttpStatus.OK);
    }

    @ApiOperation(value="회원 탈퇴 API", notes="사용자가 본인의 계정을 삭제하고 싶을 때 사용하는 API입니다.")
    @DeleteMapping
    public ResponseEntity<?> delete() {
        if (memberService.delete()) {
            return new ResponseEntity<>("true", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("false", HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value="아이디 찾기 API", notes="사용자가 본인의 이름과 전화번호를 이용해 아이디를 찾을 수 있는 API입니다.")
    @GetMapping("/find/id")
    public ResponseEntity<?> findId(@RequestBody FindIdRequestDto requestDto) {
        FindIdResponseDto responseDto = memberService.findId(requestDto);

        if (responseDto == null) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }

    }
}
