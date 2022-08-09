package com.ssafy.uknowme.web.controller;

import com.ssafy.uknowme.model.dto.RoomDto.FindRoomRequestDto;
import com.ssafy.uknowme.model.dto.RoomDto.RoomInfoResponseDto;
import com.ssafy.uknowme.web.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<?> findRoom(@ModelAttribute FindRoomRequestDto dto) {
        RoomInfoResponseDto responseDto = roomService.findRoom(dto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
