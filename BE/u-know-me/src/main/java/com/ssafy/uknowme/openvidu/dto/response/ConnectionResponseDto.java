package com.ssafy.uknowme.openvidu.dto.response;

import com.ssafy.uknowme.web.domain.Member;
import com.ssafy.uknowme.web.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;

@Getter
@Setter
@AllArgsConstructor
public class ConnectionResponseDto {

    private String token;

    private Room room;

    private Member member;
}
