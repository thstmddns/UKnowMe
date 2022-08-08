package com.ssafy.uknowme.model.dto.noticeDto;

import com.ssafy.uknowme.web.domain.Notice;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;

@Getter
public class NoticeListResponseDto {
    private String member;
    private String title;


    public NoticeListResponseDto(Notice entity) {
        this.member = String.valueOf(entity.getMember());
        this.title = entity.getTitle();
    }
}
