package com.ssafy.uknowme.web.controller;

import com.ssafy.uknowme.model.dto.MachingMemberDto;
import com.ssafy.uknowme.web.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matching")
public class MatchingController {
//초록불은 오타다
    private final MainPageService mainPageService;


    @PostMapping("/1vs1")
    public ResponseEntity<?> getMatchingUserInfo(@RequestBody MachingMemberDto dto){

        MachingMemberDto machingMemberDto = mainPageService.getMacingMemberInfo(dto);
        return new ResponseEntity(machingMemberDto, HttpStatus.OK);
    }
}
