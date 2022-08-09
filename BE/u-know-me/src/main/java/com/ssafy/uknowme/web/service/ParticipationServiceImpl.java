package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.ParticipationDto.ParticipationInfoResponseDto;
import com.ssafy.uknowme.model.dto.ParticipationDto.ParticipationSaveRequestDto;
import com.ssafy.uknowme.web.domain.Participation;
import com.ssafy.uknowme.web.repository.MemberRepository;
import com.ssafy.uknowme.web.repository.ParticipationRepository;
import com.ssafy.uknowme.web.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ParticipationServiceImpl implements ParticipationService{

    private final ParticipationRepository participationRepository;

    private final RoomRepository roomRepository;

    private final MemberRepository memberRepository;

    @Override
    public boolean save(ParticipationSaveRequestDto dto) {

        Participation participation = Participation.builder()
                .room(roomRepository.getReferenceById(dto.getRoomSeq()))
                .member(memberRepository.getReferenceById(dto.getMemberSeq()))
                .build();

        participationRepository.save(participation);

        return false;
    }

    @Override
    public ParticipationInfoResponseDto findById(int participationSeq) {

        Participation participation = participationRepository.findById(participationSeq).orElseThrow(IllegalStateException::new);

        if (participation.isDeleted()) {
            throw new IllegalStateException("사용할 수 없는 데이터입니다.");
        }

        ParticipationInfoResponseDto responseDto = new ParticipationInfoResponseDto();

        responseDto.convertFromEntity(participation);

        return responseDto;
    }

    @Override
    public boolean like(int memberSeq) {

//        participationRepository.findByMemberSeq(m)

        return false;
    }
}
