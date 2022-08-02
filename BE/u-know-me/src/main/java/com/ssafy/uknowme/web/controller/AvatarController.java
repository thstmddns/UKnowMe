package com.ssafy.uknowme.web.controller;

import com.ssafy.uknowme.model.dto.AvatarRequestDto;
import com.ssafy.uknowme.web.service.AvatarService;
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
