package com.ssafy.uknowme.openvidu.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnectionRequestDto {

    private int roomSeq;

    private String token;
}
