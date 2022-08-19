package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.noticeDto.NoticeListResponseDto;
import com.ssafy.uknowme.model.dto.noticeDto.NoticeResponseDto;
import com.ssafy.uknowme.model.dto.noticeDto.NoticeSaveRequestDto;
import com.ssafy.uknowme.model.dto.noticeDto.NoticeUpdateRequestDto;

import java.util.List;

public interface NoticeService {

    Integer save(NoticeSaveRequestDto requestDto, String loginId);

    Integer update(int noticeSeq, NoticeUpdateRequestDto requestDto);

    NoticeResponseDto findByNoticeSeq(int noticeSeq);

    List<NoticeListResponseDto> findAll();

    void delete(int noticeSeq);
}
