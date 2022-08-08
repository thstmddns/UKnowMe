package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.RoomDto.FindRoomRequestDto;
import com.ssafy.uknowme.model.dto.RoomDto.RoomInfoResponseDto;
import com.ssafy.uknowme.model.dto.RoomDto.RoomSaveRequestDto;

public interface RoomService {

    /**
     * 방 정보를 생성해 DB에 저장하는 메서드입니다.
     * @param dto
     * @return true, false
     */
    boolean save(RoomSaveRequestDto dto);

    /**
     * 방 SEQ번호로 방 정보를 조회하는 메서드입니다.
     * @param dto
     * @return FindRoomResponseDto
     */
    RoomInfoResponseDto findRoom(FindRoomRequestDto dto);

    /**
     * 해당 SEQ의 방에 balanceCount를 1 증가시키는 메서드입니다.
     * @param seq
     * @return
     */
    boolean addBalanceCount(String seq);

    /**
     * 방 정보가 삭제되는 메서드입니다.
     * @param seq
     * @return
     */
    boolean delete(String seq);
}
