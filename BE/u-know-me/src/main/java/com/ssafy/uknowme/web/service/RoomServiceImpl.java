package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.RoomDto.FindRoomRequestDto;
import com.ssafy.uknowme.model.dto.RoomDto.RoomInfoResponseDto;
import com.ssafy.uknowme.model.dto.RoomDto.RoomSaveRequestDto;
import com.ssafy.uknowme.web.domain.Room;
import com.ssafy.uknowme.web.domain.enums.RoomType;
import com.ssafy.uknowme.web.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public boolean save(RoomSaveRequestDto dto) {
        Room room = Room.builder()
                .seq(dto.getSeq())
                .type(getRoomType(dto))
                .balanceCount(0)
                .build();

        roomRepository.save(room);

        return true;
    }

    @Override
    public RoomInfoResponseDto findRoom(FindRoomRequestDto dto) {
        Room room = roomRepository.findById(dto.getSeq()).orElseThrow(IllegalStateException::new);

        if (room.isDeleted()) {
            throw new IllegalStateException("삭제된 정보입니다.");
        }

        RoomInfoResponseDto responseDto = new RoomInfoResponseDto();

        responseDto.convertFromEntity(room);

        return responseDto;
    }

    @Override
    public boolean addBalanceCount(String seq) {
        Room room = roomRepository.findById(seq).orElseThrow(IllegalStateException::new);

        if (room.isDeleted()) {
            throw new IllegalStateException("삭제된 정보입니다.");
        }

        room.addBalanceCount();

        return true;
    }

    @Override
    public boolean delete(String seq) {
        Room room = roomRepository.findById(seq).orElseThrow(IllegalStateException::new);

        if (room.isDeleted()) {
            throw new IllegalStateException("삭제된 정보입니다.");
        }

        room.delete();

        return true;
    }

    private RoomType getRoomType(RoomSaveRequestDto dto) {
        if (dto.getType().equals("match_start_1")) return RoomType.ONE;
        if (dto.getType().equals("match_start_2")) return RoomType.TWO;
        throw new IllegalStateException("잘못된 데이터입니다.");
    }
}
