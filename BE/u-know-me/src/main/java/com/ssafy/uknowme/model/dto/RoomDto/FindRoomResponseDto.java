package com.ssafy.uknowme.model.dto.RoomDto;

import com.ssafy.uknowme.web.domain.enums.RoomType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FindRoomResponseDto {

    private String seq;

    private RoomType type;

    private int balanceCount;
}
