package com.ssafy.uknowme.web.controller;


import com.ssafy.uknowme.model.dto.noticeDto.NoticeListResponseDto;
import com.ssafy.uknowme.model.dto.noticeDto.NoticeResponseDto;
import com.ssafy.uknowme.model.dto.noticeDto.NoticeSaveRequestDto;
import com.ssafy.uknowme.model.dto.noticeDto.NoticeUpdateRequestDto;
import com.ssafy.uknowme.security.annotation.AuthenticationId;
import com.ssafy.uknowme.web.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequestMapping("/notice")
@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping("/create")
    @Secured("ROLE_MANAGER")
    public boolean save(@RequestBody NoticeSaveRequestDto requestDto, @AuthenticationId String loginId) {
        noticeService.save(requestDto, loginId);
        return true;
    }

    @PutMapping("/{noticeSeq}")
    @Secured("ROLE_MANAGER")
    public boolean update(@PathVariable int noticeSeq, @RequestBody NoticeUpdateRequestDto requestDto) {
        noticeService.update(noticeSeq, requestDto);
        return true;
    }

    @GetMapping("/{noticeSeq}")
    public NoticeResponseDto findByNoticeSeq (@PathVariable int noticeSeq) {
        return noticeService.findByNoticeSeq(noticeSeq);
    }

    @GetMapping("/list")
    public List<NoticeListResponseDto> findAll() {
        return noticeService.findAll();
    }


    @DeleteMapping("/{noticeSeq}")
    @Secured("ROLE_MANAGER")
    public boolean delete(@PathVariable int noticeSeq) {
        noticeService.delete(noticeSeq);
        return true;
    }
}
