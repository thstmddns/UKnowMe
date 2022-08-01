package com.ssafy.uknowme.web.controller;

import com.ssafy.uknowme.model.dto.MemberRequestDto;
import com.ssafy.uknowme.model.dto.MemberResponseDto;
import com.ssafy.uknowme.model.dto.MemberUpdateDto;
import com.ssafy.uknowme.web.service.MemberService;
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


    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "좋은 아침");
        return "index";
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@Validated @RequestBody MemberRequestDto dto) {
        memberService.join(dto);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/check/id")
    public ResponseEntity<?> checkIdDuplication(@RequestParam(value = "memberId") String memberId) throws BadRequestException {
        System.out.println((memberId));
        if (memberService.existsByMemberId(memberId)) {
            throw new BadRequestException("이미 사용중인 아이디 입니다.");
        } else {
            return ResponseEntity.ok("사용 가능한 아이디 입니다.");
        }
    }

    @GetMapping("/check/nickname")
    public ResponseEntity<?> checkNickNameDuplication(@RequestParam(value = "memberNickname") String memberNickName) throws BadRequestException {
        System.out.println((memberNickName));
        if (memberService.existsByMemberNickName(memberNickName)) {
            throw new BadRequestException("이미 사용중인 별명 입니다.");
        } else {
            return ResponseEntity.ok("사용 가능한 별명 입니다.");
        }
    }

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

    @PutMapping("/update")
    public String update(@RequestBody MemberUpdateDto dto) {
        return memberService.update(dto);
    }

    

}
