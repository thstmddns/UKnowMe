package com.ssafy.uknowme.web.controller;

import com.ssafy.uknowme.model.dto.AvatarRequestDto;
import com.ssafy.uknowme.web.service.AvatarService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/avatars")
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarService avatarService;

    @ApiOperation(value = "아바타를 저장하는 API", notes = "관리자가 아바타를 서버에 저장하고자 할 때 사용하는 API입니다.")
    @PostMapping
    public ResponseEntity<?> saveAvatar(@ModelAttribute AvatarRequestDto avatarRequestDto, @RequestParam MultipartFile file) {
        avatarService.save(avatarRequestDto, file);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> downloadAvatar() {
        Resource resource = avatarService.downloadAvatar();

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
