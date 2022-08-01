package com.ssafy.uknowme.openvidu.dto.response;

import com.ssafy.uknowme.web.domain.Member;
import com.ssafy.uknowme.web.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConnectionResponseDto {

    private String token;

    private Room room;

    private Member member;
}
