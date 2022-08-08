package com.ssafy.uknowme.model.dto.ParticipationDto;

import com.ssafy.uknowme.model.dto.MemberDto.MemberInfoResponseDto;
import com.ssafy.uknowme.model.dto.RoomDto.RoomInfoResponseDto;
import com.ssafy.uknowme.web.domain.Participation;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ParticipationInfoResponseDto {

    private int seq;

    private MemberInfoResponseDto member;

    private RoomInfoResponseDto room;

    private LocalDateTime likeDate;

    private LocalDateTime disconnectDate;

    public void convertFromEntity(Participation participation) {

        MemberInfoResponseDto memberDto = new MemberInfoResponseDto();
        RoomInfoResponseDto roomDto = new RoomInfoResponseDto();

        member.convertFromEntity(participation.getMember());
        room.convertFromEntity(participation.getRoom());


        this.seq = participation.getSeq();
        this.member = memberDto;
        this.room = roomDto;
        this.likeDate = participation.getLikeDate();
        this.disconnectDate = participation.getDisconnectDate();
    }
}
