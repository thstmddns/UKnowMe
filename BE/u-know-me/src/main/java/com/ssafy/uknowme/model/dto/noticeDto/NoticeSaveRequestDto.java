package com.ssafy.uknowme.model.dto.noticeDto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter // getter 메소드 자동추가
@Setter
@NoArgsConstructor // 기본생성자 자동 추가 == public Notice{}
public class NoticeSaveRequestDto {
    private String title;
    private String content;
}
