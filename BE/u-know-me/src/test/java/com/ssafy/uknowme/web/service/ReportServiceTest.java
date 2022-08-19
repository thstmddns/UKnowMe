package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.ReportDto.*;
import com.ssafy.uknowme.web.domain.Member;
import com.ssafy.uknowme.web.domain.Report;
import com.ssafy.uknowme.web.domain.enums.ReportState;
import com.ssafy.uknowme.web.domain.factory.MockEntityFactory;
import com.ssafy.uknowme.web.repository.MemberRepository;
import com.ssafy.uknowme.web.repository.ReportRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("신고 서비스 단위 테스트")
class ReportServiceTest {

    @InjectMocks
    private ReportServiceImpl reportService;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("[신고] 사용자는 채팅 중 상대방을 신고할 수 있다.")
    public void report() throws Exception {
        // given
        ReportRequestDto dto = new ReportRequestDto();
        dto.setReportingMemberSeq(1);
        dto.setAccusedMemberSeq(2);
        dto.setReportState(ReportState.REPORT);

        Mockito.when(memberRepository.getReferenceById(eq(1)))
                .thenAnswer((Answer<Member>) invocation -> {
                    Member expected = MockEntityFactory.createMember();

                    ReflectionTestUtils.setField(expected, "seq", 1);

                    return expected;
                });

        Mockito.when(memberRepository.getReferenceById(eq(2)))
                .thenAnswer((Answer<Member>) invocation -> {
                    Member expected = MockEntityFactory.accusedMember();

                    ReflectionTestUtils.setField(expected, "seq", 2);

                    return expected;
                });

        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.of(0, 0, 0));
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));

        Mockito.when(reportRepository.findAllByCreateDateBetween(startDatetime, endDatetime))
                .thenAnswer((Answer<List<Report>>) invocation -> {
                    List<Report> expected = new ArrayList<>();

                    for (int i = 0; i < 5; i++) {
                        Report report = MockEntityFactory.createReport();

                        ReflectionTestUtils.setField(report, "seq", i);

                        expected.add(report);
                    }

                    return expected;
                });

        Mockito.when(memberRepository.findById(dto.getAccusedMemberSeq()))
                .thenAnswer((Answer<Optional<Member>>) invocation -> {
                    Member expected = MockEntityFactory.accusedMember();

                    ReflectionTestUtils.setField(expected, "seq", 2);

                    return Optional.of(expected);
                });

        // when
        boolean isSuccess = reportService.report(dto);

        // then
        assertThat(isSuccess).isTrue();
        verify(memberRepository, times(1)).getReferenceById(eq(1));
        verify(memberRepository, times(1)).getReferenceById(eq(2));
        verify(reportRepository, times(1)).findAllByCreateDateBetween(any(LocalDateTime.class), any(LocalDateTime.class));
        verify(memberRepository, times(1)).findById(eq(2));
    }

    @Test
    @DisplayName("[특정 유저 신고 목록 조회] 관리자는 특정 유저의 신고 목록을 조회할 수 있다.")
    public void getReportInfos() throws Exception {
        // given
        ReportInfosRequestDto dto = new ReportInfosRequestDto();
        dto.setMemberSeq(2);

        Mockito.when(reportRepository.findAll())
                .thenAnswer((Answer<List<Report>>) invocation -> {
                    List<Report> expected = new ArrayList<>();

                    for (int i = 0; i < 5; i++) {
                        Report report = MockEntityFactory.createReport();

                        ReflectionTestUtils.setField(report, "seq", i);

                        expected.add(report);
                    }

                    return expected;
                });

        // when
        List<ReportResponseDto> expected = reportService.getReportInfos(dto);

        // then
        assertThat(expected).isInstanceOf(List.class);
        assertThat(expected.size()).isEqualTo(5);
        verify(reportRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("[특정 신고 조회] 관리자는 특정 신고를 조회할 수 있다.")
    public void getReportInfo() throws Exception {
        // given
        ReportInfoRequestDto requestDto = new ReportInfoRequestDto();
        requestDto.setReportSeq(1);
        requestDto.setMemberSeq(2);

        Mockito.when(reportRepository.findById(eq(1)))
                .thenAnswer((Answer<Optional<Report>>) invocation -> {
                    Report expected = MockEntityFactory.createReport();

                    ReflectionTestUtils.setField(expected, "seq", 1);

                    return Optional.of(expected);
                });

        // when
        ReportResponseDto responseDto = reportService.getReportInfo(requestDto);

        // then
        assertThat(responseDto.getReportSeq()).isEqualTo(1);
        verify(reportRepository, times(1)).findById(eq(1));
    }

    @Test
    @DisplayName("[신고 수정] 관리자는 신고 상태를 수정할 수 있다.")
    public void update() throws Exception {
        // given
        ReportUpdateRequestDto dto = new ReportUpdateRequestDto();
        dto.setReportSeq(1);
        dto.setMemberSeq(2);
        dto.setState(ReportState.CANCEL);

        Mockito.when(reportRepository.findById(dto.getReportSeq()))
                .thenAnswer((Answer<Optional<Report>>) invocation -> {
                    Report expected = MockEntityFactory.createReport();

                    ReflectionTestUtils.setField(expected, "seq", 1);

                    return Optional.of(expected);
                });

        // when
        boolean isSuccess = reportService.update(dto);

        // then
        assertThat(isSuccess).isTrue();
        verify(reportRepository, times(1)).findById(dto.getReportSeq());
    }

    @Test
    @DisplayName("[신고 삭제] 관리자는 특정 신고를 삭제할 수 있다.")
    public void delete() throws Exception {
        // given
        ReportDeleteRequestDto dto = new ReportDeleteRequestDto();
        dto.setReportSeq(1);
        dto.setMemberSeq(2);

        Mockito.when(reportRepository.findById(dto.getReportSeq()))
                .thenAnswer((Answer<Optional<Report>>) invocation -> {
                    Report expected = MockEntityFactory.createReport();

                    ReflectionTestUtils.setField(expected, "seq", 1);

                    return Optional.of(expected);
                });

        // when
        boolean isSuccess = reportService.delete(dto);

        // then
        assertThat(isSuccess).isTrue();
        verify(reportRepository, times(1)).findById(dto.getReportSeq());
    }
}