package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.RoomDto.FindRoomRequestDto;
import com.ssafy.uknowme.model.dto.RoomDto.RoomInfoResponseDto;
import com.ssafy.uknowme.model.dto.RoomDto.RoomSaveRequestDto;
import com.ssafy.uknowme.web.domain.Room;
import com.ssafy.uknowme.web.domain.enums.RoomType;
import com.ssafy.uknowme.web.domain.factory.MockEntityFactory;
import com.ssafy.uknowme.web.repository.RoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
@DisplayName("방 정보 서비스 단위 테스트")
class RoomServiceTest {

    @InjectMocks
    private RoomServiceImpl roomService;

    @Mock
    private RoomRepository roomRepository;

    @Test
    @DisplayName("[방 생성] 서비스는 매칭 성공 시 방 정보를 자동으로 생성한다.")
    public void save() throws Exception {
        // given
        RoomSaveRequestDto dto = new RoomSaveRequestDto();
        dto.setSeq(String.valueOf(UUID.randomUUID()));
        dto.setType("match_start_1");

        // when
        boolean isSuccess = roomService.save(dto);

        // then
        assertThat(isSuccess).isTrue();
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    @DisplayName("[방 정보 조회] 서비스는 필요시 방 정보를 조회할 수 있다.")
    public void findRoom() throws Exception {
        // given
        String roomSeq = String.valueOf(UUID.randomUUID());

        FindRoomRequestDto requestDto = new FindRoomRequestDto();
        requestDto.setSeq(roomSeq);

        Mockito.when(roomRepository.findById(roomSeq))
                .thenAnswer((Answer<Optional<Room>>) invocation -> {
                    Room expected = MockEntityFactory.createRoom();

                    ReflectionTestUtils.setField(expected, "seq", roomSeq);

                    return Optional.of(expected);
                });

        // when
        RoomInfoResponseDto responseDto = roomService.findRoom(requestDto);

        // then
        assertThat(responseDto.getSeq()).isEqualTo(roomSeq);
        assertThat(responseDto.getType()).isEqualTo(RoomType.ONE);
        verify(roomRepository, times(1)).findById(roomSeq);
    }

    @Test
    @DisplayName("[밸런스 게임 횟수 증가] 사용자가 밸런스 게임을 요청할 경우 카운트를 1 증가시킨다.")
    public void addBalanceCount() throws Exception {
        // given
        String roomSeq = String.valueOf(UUID.randomUUID());

        Mockito.when(roomRepository.findById(roomSeq))
                .thenAnswer((Answer<Optional<Room>>) invocation -> {
                    Room expected = MockEntityFactory.createRoom();

                    ReflectionTestUtils.setField(expected, "seq", roomSeq);

                    return Optional.of(expected);
                });

        // when
        boolean isSuccess = roomService.addBalanceCount(roomSeq);

        // then
        assertThat(isSuccess).isTrue();
        verify(roomRepository, times(1)).findById(roomSeq);
    }

    @Test
    @DisplayName("[방 정보 삭제] 관리자는 방 정보를 삭제할 수 있다.")
    public void delete() throws Exception {
        // given
        String roomSeq = String.valueOf(UUID.randomUUID());

        Mockito.when(roomRepository.findById(roomSeq))
                .thenAnswer((Answer<Optional<Room>>) invocation -> {
                    Room expected = MockEntityFactory.createRoom();

                    ReflectionTestUtils.setField(expected, "seq", roomSeq);

                    return Optional.of(expected);
                });

        // when
        boolean isSuccess = roomService.delete(roomSeq);

        // then
        assertThat(isSuccess).isTrue();
        verify(roomRepository, times(1)).findById(roomSeq);
    }
}