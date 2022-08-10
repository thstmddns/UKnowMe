package com.ssafy.uknowme.model.dto.noticeDto;

import com.ssafy.uknowme.model.dto.MemberDto.MemberInfoResponseDto;
import com.ssafy.uknowme.web.domain.Notice;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeListResponseDto {

    private int seq;
    private MemberInfoResponseDto member;
    private String title;

    private String content;

    private LocalDateTime createDate;

    public NoticeListResponseDto(Notice entity) {
        MemberInfoResponseDto dto = new MemberInfoResponseDto();
        dto.convertFromEntity(entity.getMember());

        this.seq = entity.getSeq();
        this.member = dto;
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.createDate = entity.getCreateDate();
    }
}
