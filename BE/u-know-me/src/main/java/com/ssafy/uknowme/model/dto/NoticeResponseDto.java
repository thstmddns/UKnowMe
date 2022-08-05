package com.ssafy.uknowme.model.dto;

import com.ssafy.uknowme.web.domain.Notice;
import lombok.Getter;

@Getter
public class NoticeResponseDto {

    private String member;
    private String title;
    private String content;

    public NoticeResponseDto(Notice entity) {
        this.member = String.valueOf(entity.getMember());
        this.title = entity.getTitle();
        this.content = entity.getContent();

    }
}
