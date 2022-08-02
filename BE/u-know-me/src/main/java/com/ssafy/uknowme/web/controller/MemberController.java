package com.ssafy.uknowme.web.controller;

import com.ssafy.uknowme.model.dto.MemberRequestDto;
import com.ssafy.uknowme.model.dto.MemberResponseDto;
import com.ssafy.uknowme.model.dto.MemberUpdateDto;
import com.ssafy.uknowme.web.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value="회원 가입 API", notes="사용자가 회원 가입을 할 때 이용되는 API입니다.")
    @PostMapping("/join")
    public ResponseEntity<?> join(@Validated @RequestBody MemberRequestDto dto) {
        if (memberService.join(dto)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value="아이디 중복 검사 API", notes="사용자가 회원 가입 시 아이디 중복 검사에 이용하는 API입니다.")
    @GetMapping("/check/id")
    public ResponseEntity<?> checkIdDuplication(@RequestParam(value = "memberId") String memberId) throws BadRequestException {
        System.out.println((memberId));
        if (memberService.existsById(memberId)) {
            return new ResponseEntity<>("false", HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.ok("true");
        }
    }

    @ApiOperation(value="닉네임 중복 검사 API", notes="사용자가 회원 가입 시 닉네임 중복 검사에 이용하는 API입니다.")
    @GetMapping("/check/nickname")
    public ResponseEntity<?> checkNickNameDuplication(@RequestParam(value = "memberNickname") String memberNickName) throws BadRequestException {
        System.out.println((memberNickName));
        if (memberService.existsByNickname(memberNickName)) {
            throw new BadRequestException("false");
        } else {
            return ResponseEntity.ok("true");
        }
    }

    @ApiOperation(value="로그인 API", notes="사용자가 자신의 계정으로 로그인 할 때 이용하는 API입니다.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberRequestDto dto) {
        MemberResponseDto findDto = memberService.login(dto);

        if (findDto != null) {
            /**
             * 추가로 JWT 관리하는 코드가 들어가야 합니다.
             */
            return new ResponseEntity<>(findDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @ApiOperation(value="멤버 정보 수정 API", notes="사용자가 자신의 계정 정보를 수정하고자 할 때 사용하는 API입니다.")
    @PutMapping("/update")
    public String update(@RequestBody MemberUpdateDto dto) {
        return memberService.update(dto);
    }
}
