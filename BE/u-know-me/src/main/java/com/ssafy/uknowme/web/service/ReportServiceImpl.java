package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.MemberDto.MemberUpdateDto;
import com.ssafy.uknowme.model.dto.ReportDto.*;
import com.ssafy.uknowme.web.domain.Member;
import com.ssafy.uknowme.web.domain.Report;
import com.ssafy.uknowme.web.repository.MemberRepository;
import com.ssafy.uknowme.web.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReportServiceImpl implements  ReportService{

    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;
    @Override
    public boolean report(ReportRequestDto dto) {

        Member reportingMember = memberRepository.getReferenceById(dto.getReportingMemberSeq());
        log.info(reportingMember.getNickname());
        Member AccusedMember = memberRepository.getReferenceById(dto.getAccusedMemberSeq());
        //TODO : 명범님이 만들어놓은 Report 쓰고싶은데 어떻게 하는지를 모르겠네요,,
        Report report = Report.builder()
                .reportingMember(reportingMember)
                .accusedMember(AccusedMember)
                .state(dto.getReportState())
                .build();

        reportRepository.save(report);

        return true;
    }

    @Override
    public List<ReportResponseDto> getReportInfos(ReportInfosRequestDto dto) {
        List<Report> reportList = reportRepository.findAll();

        List<ReportResponseDto> list = new ArrayList<>();

        Member AccusedMember = memberRepository.getReferenceById(dto.getMemberSeq());

        if (!AccusedMember.getRole().toString().equals("MANAGER")) {
            throw new IllegalStateException("관리자만 정보를 변경할 수 있습니다.");
        }

        for (Report report:reportList) {
            list.add(new ReportResponseDto(
                    report.getSeq(),
                    report.getReportingMember().getSeq(),
                    report.getAccusedMember().getSeq(),

                    report.getReportingMember().getId(),
                    report.getAccusedMember().getId(),

                    report.getReportingMember().getNickname(),
                    report.getAccusedMember().getNickname(),

                    report.getState(),

                    report.getCreateDate(),
                    report.getUpdateDate(),

                    report.getCreateMember(),
                    report.getUpdateMember(),

                    report.getDeleteYn()
            ));
        }

        return list;
    }

    @Override
    public ReportResponseDto getReportInfo(ReportInfoRequestDto dto) {


        Member AccusedMember = memberRepository.getReferenceById(dto.getMemberSeq());

        if (!AccusedMember.getRole().toString().equals("MANAGER")) {
            throw new IllegalStateException("관리자만 정보를 변경할 수 있습니다.");
        }

        Optional<Report> report = reportRepository.findById(dto.getReportSeq());

         ReportResponseDto reportResponseDto = new ReportResponseDto();

        reportResponseDto.setReportseq(report.get().getSeq());
        reportResponseDto.setReportingMemberSeq(report.get().getReportingMember().getSeq());
        reportResponseDto.setAccusedMemberSeq(report.get().getReportingMember().getSeq());
        reportResponseDto.setReportingMemberId(report.get().getReportingMember().getId());
        reportResponseDto.setAccusedMemberId(report.get().getReportingMember().getId());
        reportResponseDto.setReportingMemberNickname(report.get().getReportingMember().getNickname());
        reportResponseDto.setAccusedMemberNickname(report.get().getReportingMember().getNickname());
        reportResponseDto.setState(report.get().getState());
        reportResponseDto.setCreateDate(report.get().getCreateDate());
        reportResponseDto.setUpdateDate(report.get().getUpdateDate());
        reportResponseDto.setCreateMember(report.get().getCreateMember());
        reportResponseDto.setUpdateMember(report.get().getUpdateMember());
        reportResponseDto.setDeleteYn(report.get().getDeleteYn());



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

        if(!report.isPresent()) return false;
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
