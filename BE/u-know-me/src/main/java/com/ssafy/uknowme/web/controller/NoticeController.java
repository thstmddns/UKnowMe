package com.ssafy.uknowme.web.controller;


import com.ssafy.uknowme.model.dto.noticeDto.NoticeListResponseDto;
import com.ssafy.uknowme.model.dto.noticeDto.NoticeResponseDto;
import com.ssafy.uknowme.model.dto.noticeDto.NoticeSaveRequestDto;
import com.ssafy.uknowme.model.dto.noticeDto.NoticeUpdateRequestDto;
import com.ssafy.uknowme.web.service.NoticeServiceImpl;
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

    private final NoticeServiceImpl noticeService;

    @PostMapping("/create")
    public boolean save(@RequestBody NoticeSaveRequestDto requestDto) {
        noticeService.save(requestDto);
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
        NoticeResponseDto responseDto = noticeService.findByNoticeSeq(noticeSeq);
        return responseDto;
    }

    @GetMapping("/list")
    public List<NoticeListResponseDto> findAll() {
        List<NoticeListResponseDto> notices = noticeService.findAll();
        return notices;
    }


    @DeleteMapping("/{noticeSeq}")
    @Secured("ROLE_MANAGER")
    public boolean delete(@PathVariable int noticeSeq) {
        noticeService.delete(noticeSeq);
        return true;
    }
}
