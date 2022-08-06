package com.ssafy.uknowme.web.controller;

import com.ssafy.uknowme.model.dto.AvatarDto.AvatarSaveRequestDto;
import com.ssafy.uknowme.web.service.AvatarService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/avatars")
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarService avatarService;

    @ApiOperation(value = "아바타를 저장하는 API", notes = "관리자가 아바타를 서버에 저장하고자 할 때 사용하는 API입니다.")
    @PostMapping
    public ResponseEntity<?> saveAvatar(@ModelAttribute AvatarSaveRequestDto avatarRequestDto) {

        avatarService.save(avatarRequestDto);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @ApiOperation(value = "아바타 모델 다운로드 API", notes = "현재 로그인한 회원이 착용하고 있는 아바타를 다운로드합니다.")
    @GetMapping(value="/vrm")
    public ResponseEntity<?> downloadVrmFileByAuthentication() {
        Resource resource = avatarService.downloadVrmFileByAuthentication();
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @ApiOperation(value = "아바타 썸네일 이미지 다운로드 API", notes = "현재 로그인한 회원이 착용하고 있는 아바타의 이미지를 다운로드합니다.")
    @GetMapping(value="/image")
    public ResponseEntity<?> downloadImageFileByAuthentication() {
        Resource resource = avatarService.downloadImageFileByAuthentication();
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @ApiOperation(value = "아바타 모델 다운로드 API", notes = "현재 로그인한 회원이 착용하고 있는 아바타를 다운로드합니다.")
    @GetMapping(value="/{avatarSeq}/vrm")
    public ResponseEntity<?> downloadVrmFileBySeq(@PathVariable int avatarSeq) {
        Resource resource = avatarService.downloadVrmFileBySeq(avatarSeq);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @ApiOperation(value = "아바타 썸네일 이미지 다운로드 API", notes = "현재 로그인한 회원이 착용하고 있는 아바타의 이미지를 다운로드합니다.")
    @GetMapping(value="/{avatarSeq}/image")
    public ResponseEntity<?> downloadImageFileBySeq(@PathVariable int avatarSeq) {
        Resource resource = avatarService.downloadImageFileBySeq(avatarSeq);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @ApiOperation(value = "모든 아바타의 정보를 가져오는 API", notes = "서버에 저장된 모든 아바타 리스트를 가져옵니다.")
    @GetMapping
    public ResponseEntity<?> findAllDto() {
        return new ResponseEntity<>(avatarService.findAllDto(), HttpStatus.OK);
    }
}
