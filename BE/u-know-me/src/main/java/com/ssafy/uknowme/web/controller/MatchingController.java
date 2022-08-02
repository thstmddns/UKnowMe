package com.ssafy.uknowme.web.controller;

import com.ssafy.uknowme.model.dto.MatchingDto.MatchingRequestDto;
import com.ssafy.uknowme.model.dto.MatchingDto.MatchingResponseDto;
import com.ssafy.uknowme.web.service.MatchingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(value="sa")
@RestController
@RequiredArgsConstructor
@RequestMapping("/matching")
public class MatchingController {
//초록불은 오타다
    private final MatchingService matchingService;


    @ApiOperation(value = "1대1 매칭 시 전달받을 정보를 담은 API", notes = "사용자의 id 를 통해 사용자의 정보를 조회한다")
    @PostMapping("/1vs1")
    public ResponseEntity<?> get1vs1MatchingUserInfo(@RequestBody MatchingRequestDto dto) throws Exception {

        MatchingResponseDto matchingMemberDto = matchingService.get1vs1MatchingMemberInfo(dto);
        if(matchingMemberDto.getNickname()!=null & matchingMemberDto.getBirth() !=null
        &matchingMemberDto.getSmoke() !=null & matchingMemberDto.getGender() != null){
            return new ResponseEntity(matchingMemberDto, HttpStatus.OK);
        }else{
            return new ResponseEntity(matchingMemberDto, HttpStatus.NO_CONTENT);
        }

    }

    @ApiOperation(value = "2대2 매칭 시 전달받을 정보를 담은 API", notes = "사용자의 id 를 통해 사용자의 정보를 조회한다")
    @PostMapping("/2vs2")
    public ResponseEntity<?> get2vs2MatchingUserInfo(@RequestBody MatchingRequestDto dto) throws Exception {

        MatchingResponseDto matchingMemberDto = matchingService.get2vs2MatchingMemberInfo(dto);
        if(matchingMemberDto.getNickname()!=null & matchingMemberDto.getBirth() !=null
                & matchingMemberDto.getGender() != null){
            return new ResponseEntity(matchingMemberDto, HttpStatus.OK);
        }else{
            return new ResponseEntity(matchingMemberDto, HttpStatus.NO_CONTENT);
        }

    }
}
