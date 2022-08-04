package com.ssafy.uknowme.web.controller;


import com.ssafy.uknowme.model.dto.NoticeListResponseDto;
import com.ssafy.uknowme.model.dto.NoticeResponseDto;
import com.ssafy.uknowme.model.dto.NoticeSaveRequestDto;
import com.ssafy.uknowme.model.dto.NoticeUpdateRequestDto;
import com.ssafy.uknowme.web.domain.Notice;
import com.ssafy.uknowme.web.repository.NoticeRepository;
import com.ssafy.uknowme.web.service.NoticeServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequestMapping("/notice")
@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeServiceImpl noticeService;
    @Autowired
    private NoticeRepository noticeRepository;

    @PostMapping("/create")
    public boolean save(@RequestBody NoticeSaveRequestDto requestDto) {
        return true;
    }

    @PutMapping("/{noticeSeq}")
    public boolean update(@PathVariable int noticeSeq, @RequestBody NoticeUpdateRequestDto requestDto) {
        return true;
    }

    @GetMapping("/{noticeSeq}")
    public NoticeResponseDto findByNoticeSeq (@PathVariable int noticeSeq) {
        return noticeService.findByNoticeSeq(noticeSeq);
    }

    @GetMapping("/list")
    public boolean notice(Model model) {
        List<Notice> notices = noticeRepository.findAll();
        model.addAttribute("notice", notices);
        return true;
    }


    @DeleteMapping("/{noticeSeq}")
    public boolean delete(@PathVariable int noticeSeq) {
        noticeService.delete(noticeSeq);
        return true;
    }
}
