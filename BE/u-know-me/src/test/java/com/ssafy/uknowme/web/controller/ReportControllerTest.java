package com.ssafy.uknowme.web.controller;

import com.ssafy.uknowme.model.dto.ReportDto.*;
import com.ssafy.uknowme.web.domain.Report;
import com.ssafy.uknowme.web.domain.enums.ReportState;
import com.ssafy.uknowme.web.domain.factory.MockEntityFactory;
import com.ssafy.uknowme.web.service.ReportService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static com.ssafy.uknowme.utils.JacksonUtils.convertToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("신고 컨트롤러")
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @Test
    @WithMockUser
    @DisplayName("[신고] 사용자는 상대 유저를 신고할 수 있다.")
    public void createReport() throws Exception {
        // given
        ReportRequestDto dto = new ReportRequestDto();

        dto.setReportingMemberSeq(1);
        dto.setAccusedMemberSeq(2);

        Mockito.when(reportService.report(any(ReportRequestDto.class))).thenReturn(true);

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/report/create")
                .content(convertToJson(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().string("true"));
        verify(reportService, times(1)).report(any(ReportRequestDto.class));
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    @DisplayName("[특정 신고 조회] 관리자는 특정 신고를 조회할 수 있다.")
    public void getReport() throws Exception {
        // given
        Report expectedReport = MockEntityFactory.createReport();

        ReportResponseDto expected = new ReportResponseDto();
        expected.convertToEntity(expectedReport);

        Mockito.when(reportService.getReportInfo(any(ReportInfoRequestDto.class))).thenReturn(expected);

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/report/read/info?reportSeq=1&memberSeq=2")
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().json(convertToJson(expected)));
        verify(reportService, times(1)).getReportInfo(any(ReportInfoRequestDto.class));
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    @DisplayName("[특정 피신고자 신고 리스트 조회] 관리자는 신고 목록을 조회할 수 있다.")
    public void getReportList() throws Exception {
        // given
        Report expectedReport = MockEntityFactory.createReport();

        ReportResponseDto dto = new ReportResponseDto();
        dto.convertToEntity(expectedReport);

        List<ReportResponseDto> expected = new ArrayList<>();
        expected.add(dto);

        Mockito.when(reportService.getReportInfos(any(ReportInfosRequestDto.class))).thenReturn(expected);

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/report/read/infos?memberSeq=2")
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().json(convertToJson(expected)));
        verify(reportService, times(1)).getReportInfos(any(ReportInfosRequestDto.class));
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    @DisplayName("[특정 신고 수정] 관리자는 특정 신고를 수정할 수 있다.")
    public void update() throws Exception {
        // given
        ReportUpdateRequestDto dto = new ReportUpdateRequestDto();
        dto.setReportSeq(1);
        dto.setMemberSeq(2);
        dto.setState(ReportState.CANCEL);

        Mockito.when(reportService.update(any(ReportUpdateRequestDto.class))).thenReturn(true);

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.put("/report/modify")
                .content(convertToJson(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().string("true"));
        verify(reportService, times(1)).update(any(ReportUpdateRequestDto.class));

    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    @DisplayName("[특정 신고 삭제] 관리자는 특정 신고를 삭제할 수 있다.")
    public void delete() throws Exception {
        // given
        ReportDeleteRequestDto dto = new ReportDeleteRequestDto();
        dto.setReportSeq(1);
        dto.setMemberSeq(2);

        Mockito.when(reportService.delete(any(ReportDeleteRequestDto.class))).thenReturn(true);

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.delete("/report/delete")
                .content(convertToJson(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().string("true"));
        verify(reportService, times(1)).delete(any(ReportDeleteRequestDto.class));
    }
}