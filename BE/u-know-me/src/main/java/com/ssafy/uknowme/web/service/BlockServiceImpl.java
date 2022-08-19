package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.BlockDto.BlockInfoRequestDto;
import com.ssafy.uknowme.model.dto.BlockDto.BlockRequestDto;
import com.ssafy.uknowme.model.dto.BlockDto.BlockResponseDto;
import com.ssafy.uknowme.model.dto.ReportDto.*;
import com.ssafy.uknowme.web.domain.Block;
import com.ssafy.uknowme.web.domain.Member;
import com.ssafy.uknowme.web.domain.Report;
import com.ssafy.uknowme.web.domain.enums.BlockState;
import com.ssafy.uknowme.web.domain.enums.ReportState;
import com.ssafy.uknowme.web.repository.BlockRepository;
import com.ssafy.uknowme.web.repository.MemberRepository;
import com.ssafy.uknowme.web.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BlockServiceImpl implements BlockService {

    private final BlockRepository blockRepository;
    private final MemberRepository memberRepository;

    @Override
    public boolean block(BlockRequestDto dto) {

        Member blockingMember = memberRepository.getReferenceById(dto.getBlokingMemberSeq());
        Member blockedMember = memberRepository.getReferenceById(dto.getBlockedMemberSeq());

        Block block = Block.builder()
                .blockingMember(blockingMember)
                .blockedMember(blockedMember)
                .state(BlockState.BLOCK)
                .build();

       blockRepository.save(block);

        return true;
    }

    @Override
    public List<BlockResponseDto> getBlockInfos(BlockInfoRequestDto dto) {
        List<Block> BlockList = blockRepository.findByBlockingMemberSeq(dto.getBlokingMemberSeq());

        List<BlockResponseDto> list = new ArrayList<>();

        for (Block block : BlockList) {
            BlockResponseDto responseDto = new BlockResponseDto();

            responseDto.convertToEntity( block);

            list.add(responseDto);
        }

        return list;
    }


    @Override
    public boolean update(BlockRequestDto dto) {

        Block block = blockRepository.findById(dto.getBlockedMemberSeq()).orElseThrow(IllegalStateException::new);
        block.updateBlock(dto.getState());

        return true;
    }





}
