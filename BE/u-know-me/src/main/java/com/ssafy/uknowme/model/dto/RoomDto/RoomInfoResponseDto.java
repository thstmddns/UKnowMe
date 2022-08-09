package com.ssafy.uknowme.model.dto.RoomDto;

import com.ssafy.uknowme.web.domain.Room;
import com.ssafy.uknowme.web.domain.enums.RoomType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RoomInfoResponseDto {

    private String seq;

    private RoomType type;

    private int balanceCount;

    public void convertFromEntity(Room room) {
        this.seq = room.getSeq();
        this.type = room.getType();
        this.balanceCount = room.getBalanceCount();
    }
}
