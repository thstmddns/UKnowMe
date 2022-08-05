package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.MemberUpdateDto;
import com.ssafy.uknowme.model.dto.NoticeUpdateRequestDto;

public interface NoticeService {

    String update(NoticeUpdateRequestDto noticeUpdateRequestDto);
}
