package com.ssafy.uknowme.web.controller;


import com.ssafy.uknowme.model.dto.NoticeResponseDto;
import com.ssafy.uknowme.model.dto.NoticeSaveRequestDto;
import com.ssafy.uknowme.model.dto.NoticeUpdateRequestDto;
import com.ssafy.uknowme.web.service.NoticeServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeServiceImpl noticeService;

    @PostMapping("/notice")
    public Integer save(@RequestBody NoticeSaveRequestDto requestDto) {
        return noticeService.save(requestDto);
    }

    @PutMapping("/notice/{noticeSeq}")
    public Integer update(@PathVariable int noticeSeq, @RequestBody NoticeUpdateRequestDto requestDto) {
        return noticeService.update(noticeSeq, requestDto);
    }

    @GetMapping("/notice/{noticeSeq}")
    public NoticeResponseDto findByNoticeSeq (@PathVariable int noticeSeq) {
        return noticeService.findByNoticeSeq(noticeSeq);
    }

    @DeleteMapping("/notice/{noticeSeq}")
    public Integer delete(@PathVariable int noticeSeq) {
        noticeService.delete(noticeSeq);
        return noticeSeq;
    }
}
