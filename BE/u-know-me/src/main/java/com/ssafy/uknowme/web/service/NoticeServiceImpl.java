package com.ssafy.uknowme.web.service;


import com.ssafy.uknowme.model.dto.NoticeResponseDto;
import com.ssafy.uknowme.model.dto.NoticeSaveRequestDto;
import com.ssafy.uknowme.model.dto.NoticeUpdateRequestDto;
import com.ssafy.uknowme.web.domain.Member;
import com.ssafy.uknowme.web.domain.Notice;
import com.ssafy.uknowme.web.repository.MemberRepository;
import com.ssafy.uknowme.web.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class NoticeServiceImpl {

    private final NoticeRepository noticeRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public Integer save(NoticeSaveRequestDto requestDto) {
        Notice notice = toEntity(requestDto);
        noticeRepository.save(notice);
        return notice.getSeq();
    }

    public Notice toEntity(NoticeSaveRequestDto dto) {
        Optional<Member> findMember = memberRepository.findById(dto.getMemberSeq());
        return Notice.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .member(findMember.orElseThrow(() -> new IllegalStateException("멤버 정보가 없습니다.")))
                .hit(0)
                .build();
    }

    @Transactional

    public Integer update(int noticeSeq, NoticeUpdateRequestDto requestDto) {
        Notice notice = (Notice) noticeRepository.findBySeq(noticeSeq).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        notice.update(requestDto.getTitle(), requestDto.getContent());

        return noticeSeq;
    }
    public NoticeResponseDto findByNoticeSeq (int noticeSeq) {
        Notice entity = (Notice) noticeRepository.findBySeq(noticeSeq).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return new NoticeResponseDto(entity);
    }

    @Transactional
    public void delete (int noticeSeq) {

            Notice notice = noticeRepository.findBySeq(noticeSeq).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
            noticeRepository.delete(notice);
    }
}