package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.ParticipationDto.ParticipationInfoResponseDto;
import com.ssafy.uknowme.model.dto.ParticipationDto.ParticipationSaveRequestDto;
import com.ssafy.uknowme.web.domain.Member;
import com.ssafy.uknowme.web.domain.Participation;
import com.ssafy.uknowme.web.domain.Room;
import com.ssafy.uknowme.web.domain.factory.MockEntityFactory;
import com.ssafy.uknowme.web.repository.MemberRepository;
import com.ssafy.uknowme.web.repository.ParticipationRepository;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
@DisplayName("참가자 서비스 단위 테스트")
class ParticipationServiceTest {

    @InjectMocks
    private ParticipationServiceImpl participationService;

    @Mock
    private ParticipationRepository participationRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("[참가자 저장] 서비스는 매칭이 성공했을 때 참가자의 정보를 DB에 저장한다.")
    public void save() throws Exception {
        // given
        ParticipationSaveRequestDto dto = new ParticipationSaveRequestDto();

        dto.setRoomSeq(String.valueOf(UUID.randomUUID()));
        dto.setMemberSeq(1);

        Mockito.when(roomRepository.getReferenceById(dto.getRoomSeq()))
                .thenAnswer((Answer<Room>) invocation -> {
                    Room expected = MockEntityFactory.createRoom();

                    ReflectionTestUtils.setField(expected, "seq", dto.getRoomSeq());

                    return expected;
                });

        Mockito.when(memberRepository.getReferenceById(dto.getMemberSeq()))
                .thenAnswer((Answer<Member>) invocation -> {
                    Member expected = MockEntityFactory.createMember();

                    ReflectionTestUtils.setField(expected, "seq", dto.getMemberSeq());

                    return expected;
                });

        // when
        boolean isFailed = participationService.save(dto);

        // then
        assertThat(isFailed).isFalse();
        verify(roomRepository, times(1)).getReferenceById(dto.getRoomSeq());
        verify(memberRepository, times(1)).getReferenceById(dto.getMemberSeq());
        verify(participationRepository, times(1)).save(any(Participation.class));
    }

    @Test
    @DisplayName("[참가자 조회] 서비스는 참가자 정보를 조회할 수 있다.")
    public void findById() throws Exception {
        // given
        Mockito.when(participationRepository.findById(eq(1)))
                .thenAnswer((Answer<Optional<Participation>>) invocation -> {
                    Participation expected = MockEntityFactory.createParticipation();
                    ReflectionTestUtils.setField(expected, "seq", 1);

                    return Optional.of(expected);
                });

        // when
        ParticipationInfoResponseDto dto = participationService.findById(1);

        // then
        assertThat(dto.getSeq()).isEqualTo(1);
        verify(participationRepository, times(1)).findById(eq(1));
    }
}