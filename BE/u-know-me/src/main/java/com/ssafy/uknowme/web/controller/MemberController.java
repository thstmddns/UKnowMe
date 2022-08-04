package com.ssafy.uknowme.web.controller;

import com.ssafy.uknowme.model.dto.MemberRequestDto;
import com.ssafy.uknowme.model.dto.MemberUpdateDto;
import com.ssafy.uknowme.web.exception.BadRequestException;
import com.ssafy.uknowme.web.service.MemberService;
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


    @PostMapping("/join")
    public ResponseEntity<?> join(@Validated @RequestBody MemberRequestDto dto) {
        if (memberService.join(dto)) {
            return new ResponseEntity<>("true", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("false", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/check/id")
    public ResponseEntity<?> checkIdDuplication(@RequestParam(value = "memberId") String memberId) throws BadRequestException {
        if (memberService.existsById(memberId)) {
            return new ResponseEntity<>("false", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("true", HttpStatus.OK);
        }
    }

    @GetMapping("/check/nickname")
    public ResponseEntity<?> checkNickNameDuplication(@RequestParam(value = "memberNickname") String memberNickname) throws BadRequestException {
        if (memberService.existsByNickname(memberNickname)) {
            return new ResponseEntity<>("false", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("true", HttpStatus.OK);
        }
    }

    @PutMapping("/update")
    public String update(@RequestBody MemberUpdateDto dto) {
        return memberService.update(dto);
    }

}
