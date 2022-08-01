package com.ssafy.uknowme.web.controller;

import com.ssafy.uknowme.model.dto.MatchingRequestDto;
import com.ssafy.uknowme.model.dto.MatchingResponseDto;
import com.ssafy.uknowme.web.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableAsync
@EnableWebMvc
@EnableSwagger2
@RestController
@RequiredArgsConstructor
@RequestMapping("/matching")
public class MatchingController {
//초록불은 오타다
    private final MatchingService matchingService;


    @PostMapping("/1vs1")
    public ResponseEntity<?> getMatchingUserInfo(@RequestBody MatchingRequestDto dto) throws Exception {

        MatchingResponseDto matchingMemberDto = matchingService.getMatchingMemberInfo(dto);
        return new ResponseEntity(matchingMemberDto, HttpStatus.OK);
    }
}
