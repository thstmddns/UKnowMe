package com.ssafy.uknowme.web.service.notice;

import com.ssafy.uknowme.model.dto.noticeDto.NoticeUpdateRequestDto;

public interface NoticeService {

    String update(NoticeUpdateRequestDto noticeUpdateRequestDto);
}
