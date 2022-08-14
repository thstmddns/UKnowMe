package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.noticeDto.NoticeListResponseDto;
import com.ssafy.uknowme.model.dto.noticeDto.NoticeResponseDto;
import com.ssafy.uknowme.model.dto.noticeDto.NoticeSaveRequestDto;
import com.ssafy.uknowme.model.dto.noticeDto.NoticeUpdateRequestDto;
import com.ssafy.uknowme.web.domain.Member;
import com.ssafy.uknowme.web.domain.Notice;
import com.ssafy.uknowme.web.domain.factory.MockEntityFactory;
import com.ssafy.uknowme.web.repository.MemberRepository;
import com.ssafy.uknowme.web.repository.NoticeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("공지사항 서비스 단위 테스트")
class NoticeServiceTest {

    @InjectMocks
    private NoticeServiceImpl noticeService;

    @Mock
    private NoticeRepository noticeRepository;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("[공지사항 등록] 관리자는 공지사항을 생성할 수 있다.")
    public void join() throws Exception {
        // given
        NoticeSaveRequestDto dto = new NoticeSaveRequestDto();

        dto.setTitle("공지사항");
        dto.setContent("내용");

        Notice expected = MockEntityFactory.createNotice();

        Mockito.when(noticeRepository.save(any(Notice.class)))
                .thenAnswer((Answer<Notice>) invocation -> {
                    ReflectionTestUtils.setField(expected, "seq", 1);
                    return expected;
                });

        Mockito.when(memberRepository.findById(eq("mungmnb777")))
                .thenAnswer((Answer<Optional<Member>>) invocation -> {
                    Member member = MockEntityFactory.createMember();
                    ReflectionTestUtils.setField(member, "seq", 1);
                    return Optional.of(member);
                });

        // when
        Integer seq = noticeService.save(dto, "mungmnb777");

        // then
        assertThat(seq).isEqualTo(1);
        verify(noticeRepository, times(1)).save(any(Notice.class));
        verify(memberRepository, times(1)).findById(eq("mungmnb777"));
    }

    @Test
    @DisplayName("[공지사항 수정] 관리자는 공지사항을 수정할 수 있다.")
    public void update() throws Exception {
        // given
        NoticeUpdateRequestDto dto = new NoticeUpdateRequestDto();

        dto.setTitle("수정 공지사항");
        dto.setContent("수정 내용");

        Mockito.when(noticeRepository.findBySeq(eq(1)))
                .thenAnswer((Answer<Optional<Notice>>) invocation -> {
                    Notice expected = MockEntityFactory.createNotice();
                    ReflectionTestUtils.setField(expected, "seq", 1);
                    return Optional.of(expected);
                });

        // when
        Integer seq = noticeService.update(1, dto);

        // then
        assertThat(seq).isEqualTo(1);

        verify(noticeRepository, times(1)).findBySeq(eq(1));
    }

    @Test
    @DisplayName("[공지사항 조회] 사용자는 관리자가 작성한 공지사항을 조회할 수 있다.")
    public void findByNoticeSeq() throws Exception {
        // given
        Mockito.when(noticeRepository.findBySeq(eq(1)))
                .thenAnswer((Answer<Optional<Notice>>) invocation -> {
                    Notice expected = MockEntityFactory.createNotice();

                    ReflectionTestUtils.setField(expected, "seq", 1);

                    return Optional.of(expected);
                });

        // when
        NoticeResponseDto dto = noticeService.findByNoticeSeq(1);

        // then
        assertThat(dto.getSeq()).isEqualTo(1);
        verify(noticeRepository, times(1)).findBySeq(eq(1));
    }

    @Test
    @DisplayName("[공지사항 전체 조회] 사용자는 관리자가 작성한 공지사항 리스트를 조회할 수 있다.")
    public void findAll() throws Exception {
        // given
        Mockito.when(noticeRepository.findAll())
                .thenAnswer((Answer<List<Notice>>) invocation -> {
                    Notice notice = MockEntityFactory.createNotice();

                    ReflectionTestUtils.setField(notice, "seq", 1);

                    List<Notice> expected = new ArrayList<>();
                    expected.add(notice);

                    return expected;
                });

        // when
        List<NoticeListResponseDto> dto = noticeService.findAll();

        // then
        assertThat(dto).isInstanceOf(List.class);
        verify(noticeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("[공지사항 삭제] 관리자는 공지사항을 삭제할 수 있다.")
    public void delete() throws Exception {
        // given
        Notice expected = MockEntityFactory.createNotice();

        ReflectionTestUtils.setField(expected, "seq", 1);

        Mockito.when(noticeRepository.findBySeq(1)).thenReturn(Optional.of(expected));

        // when
        noticeService.delete(1);

        // then
        verify(noticeRepository, times(1)).findBySeq(1);
        verify(noticeRepository, times(1)).delete(expected);
    }
}