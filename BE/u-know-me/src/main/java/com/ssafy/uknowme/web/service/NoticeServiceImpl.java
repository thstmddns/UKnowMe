package com.ssafy.uknowme.web.service;


import com.ssafy.uknowme.model.dto.noticeDto.NoticeListResponseDto;
import com.ssafy.uknowme.model.dto.noticeDto.NoticeResponseDto;
import com.ssafy.uknowme.model.dto.noticeDto.NoticeSaveRequestDto;
import com.ssafy.uknowme.model.dto.noticeDto.NoticeUpdateRequestDto;
import com.ssafy.uknowme.security.auth.PrincipalDetails;
import com.ssafy.uknowme.web.domain.Member;
import com.ssafy.uknowme.web.domain.Notice;
import com.ssafy.uknowme.web.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class NoticeServiceImpl {

    @Autowired
    private final NoticeRepository noticeRepository;

    @Transactional
    public Integer save(NoticeSaveRequestDto requestDto) {
        Notice notice = toEntity(requestDto);
        noticeRepository.save(notice);
        return notice.getSeq();
    }

    public Notice toEntity(NoticeSaveRequestDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principal = (PrincipalDetails) authentication.getDetails();
        Member member = principal.getMember();

        return Notice.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .member(member)
                .hit(0)
                .build();
    }

    @Transactional
    public Integer update(int noticeSeq, NoticeUpdateRequestDto requestDto) {
        Notice notice = noticeRepository.findBySeq(noticeSeq).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        notice.update(requestDto.getTitle(), requestDto.getContent());

        return noticeSeq;
    }
    public NoticeResponseDto findByNoticeSeq (int noticeSeq) {
        Notice entity = noticeRepository.findBySeq(noticeSeq).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return new NoticeResponseDto(entity);
    }
    @Transactional
    public List<NoticeListResponseDto> findAll(){
        return noticeRepository.findAll().stream()
                .map(NoticeListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete (int noticeSeq) {
        Notice notice = noticeRepository.findBySeq(noticeSeq).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        noticeRepository.delete(notice);
    }

}