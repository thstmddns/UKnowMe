package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.MemberDto.MemberUpdateDto;
import com.ssafy.uknowme.model.dto.ReportDto.*;
import com.ssafy.uknowme.web.domain.Member;
import com.ssafy.uknowme.web.domain.Report;
import com.ssafy.uknowme.web.domain.enums.ReportState;
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
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;

    @Override
    public boolean report(ReportRequestDto dto) {

        Member reportingMember = memberRepository.getReferenceById(dto.getReportingMemberSeq());
        Member accusedMember = memberRepository.getReferenceById(dto.getAccusedMemberSeq());

        Report report = Report.builder()
                .reportingMember(reportingMember)
                .accusedMember(accusedMember)
                .state(ReportState.REPORT)
                .build();

        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.of(0, 0, 0));
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));

        //이번 주 내의 신고 횟수가 5회 이상이면 피신고자의 member 의 ReportState 를 Report 로 바꾼다.
        if (reportRepository.findAllByCreateDateBetween(startDatetime, endDatetime).size() >= 4) {
            Member member = memberRepository.findById(dto.getAccusedMemberSeq()).orElseThrow(IllegalStateException::new);

            member.updateReport(ReportState.REPORT);
            member.updateReportLastDate(LocalDateTime.of(LocalDate.now().plusDays(7), LocalTime.of(23, 59, 59)));

        }
        reportRepository.save(report);

        return true;
    }

    @Override
    public List<ReportResponseDto> getReportInfos(ReportInfosRequestDto dto) {
        List<Report> reportList = reportRepository.findAll();

        List<ReportResponseDto> list = new ArrayList<>();

        Member accusedMember = memberRepository.getReferenceById(dto.getMemberSeq());

        if (!accusedMember.getRole().toString().equals("MANAGER")) {
            throw new IllegalStateException("관리자만 정보를 변경할 수 있습니다.");
        }

        for (Report report : reportList) {
            ReportResponseDto responseDto = new ReportResponseDto();

            responseDto.convertToEntity(report);

            list.add(responseDto);
        }

        return list;
    }

    @Override
    public ReportResponseDto getReportInfo(ReportInfoRequestDto dto) {


        Member AccusedMember = memberRepository.getReferenceById(dto.getMemberSeq());

        if (!AccusedMember.getRole().toString().equals("MANAGER")) {
            throw new IllegalStateException("관리자만 정보를 변경할 수 있습니다.");
        }

        Report report = reportRepository.findById(dto.getReportSeq()).orElseThrow(IllegalStateException::new);
        ReportResponseDto reportResponseDto = new ReportResponseDto();

        reportResponseDto.convertToEntity(report);

        return reportResponseDto;

    }

    @Override
    public boolean update(ReportUpdateRequestDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.info("로그인한 회원이 아닙니다.");
            return false;
        }

        Member AccusedMember = memberRepository.getReferenceById(dto.getMemberSeq());

        if (!AccusedMember.getRole().toString().equals("MANAGER")) {
            log.info("관리자만 정보를 변경할 수 있습니다.");
            return false;
        }

        Optional<Report> report = reportRepository.findById(dto.getReportSeq());

        if (!report.isPresent()) return false;
        report.get().updateReport(dto.getState());

        return true;
    }

    @Override
    public boolean delete(ReportDeleteRequestDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.info("로그인한 회원이 아닙니다.");
            return false;
        }

        Member AccusedMember = memberRepository.getReferenceById(dto.getMemberSeq());
        log.info(AccusedMember.getRole().toString());
        if (!AccusedMember.getRole().toString().equals("MANAGER")) {
            log.info("관리자만 정보를 변경할 수 있습니다.");
            return false;
        }

        Report report = reportRepository.findById(dto.getReportSeq()).orElseThrow(() -> new IllegalStateException("해당 report가 없습니다."));

        report.delete();

        return true;
    }


}
