package com.ssafy.uknowme.web.service;


import com.ssafy.uknowme.model.dto.noticeDto.NoticeListResponseDto;
import com.ssafy.uknowme.model.dto.noticeDto.NoticeResponseDto;
import com.ssafy.uknowme.model.dto.noticeDto.NoticeSaveRequestDto;
import com.ssafy.uknowme.model.dto.noticeDto.NoticeUpdateRequestDto;
import com.ssafy.uknowme.web.domain.Member;
import com.ssafy.uknowme.web.domain.Notice;
import com.ssafy.uknowme.web.repository.MemberRepository;
import com.ssafy.uknowme.web.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Integer save(NoticeSaveRequestDto requestDto, String loginId) {

        Member member = memberRepository.findById(loginId).orElseThrow(IllegalStateException::new);

        Notice notice = Notice.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .member(member)
                .hit(0)
                .build();

        noticeRepository.save(notice);

        return notice.getSeq();
    }

    @Override
    @Transactional
    public Integer update(int noticeSeq, NoticeUpdateRequestDto requestDto) {
        Notice notice = noticeRepository.findBySeq(noticeSeq).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        notice.update(requestDto.getTitle(), requestDto.getContent());

        return noticeSeq;
    }

    @Override
    @Transactional(readOnly = true)
    public NoticeResponseDto findByNoticeSeq (int noticeSeq) {
        Notice entity = noticeRepository.findBySeq(noticeSeq).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return new NoticeResponseDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoticeListResponseDto> findAll(){
        return noticeRepository.findAll().stream()
                .map(NoticeListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete (int noticeSeq) {
        Notice notice = noticeRepository.findBySeq(noticeSeq).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        noticeRepository.delete(notice);
    }

}