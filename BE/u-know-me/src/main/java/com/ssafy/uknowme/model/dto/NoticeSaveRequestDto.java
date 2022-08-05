package com.ssafy.uknowme.model.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter // getter 메소드 자동추가
@NoArgsConstructor // 기본생성자 자동 추가 == public Notice{}
public class NoticeSaveRequestDto {
    private String title;
    private String content;
    private int memberSeq;
    private int hit;

}
