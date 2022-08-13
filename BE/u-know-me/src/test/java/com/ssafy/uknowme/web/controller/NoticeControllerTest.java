package com.ssafy.uknowme.web.controller;

import com.ssafy.uknowme.model.dto.MemberDto.MemberInfoResponseDto;
import com.ssafy.uknowme.model.dto.noticeDto.NoticeListResponseDto;
import com.ssafy.uknowme.model.dto.noticeDto.NoticeResponseDto;
import com.ssafy.uknowme.model.dto.noticeDto.NoticeSaveRequestDto;
import com.ssafy.uknowme.model.dto.noticeDto.NoticeUpdateRequestDto;
import com.ssafy.uknowme.web.domain.Notice;
import com.ssafy.uknowme.web.domain.factory.MockEntityFactory;
import com.ssafy.uknowme.web.service.NoticeService;
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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.ssafy.uknowme.utils.JacksonUtils.convertToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("공지사항 컨트롤러")
class NoticeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoticeService noticeService;

    @Test
    @WithMockUser(username = "mungmnb777", roles = {"MANAGER"})
    @DisplayName("[공지사항 등록] 관리자는 공지사항을 생성할 수 있다.")
    public void save() throws Exception {
        // given
        NoticeSaveRequestDto dto = new NoticeSaveRequestDto();
        dto.setTitle("공지사항");
        dto.setContent("내용");

        Notice expected = MockEntityFactory.createNotice();
        ReflectionTestUtils.setField(expected, "seq", 1);

        Mockito.when(noticeService.save(any(NoticeSaveRequestDto.class), eq("mungmnb777"))).thenReturn(expected.getSeq());

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/notice/create")
                .content(convertToJson(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().string("true"));
        verify(noticeService, times(1)).save(any(NoticeSaveRequestDto.class), eq("mungmnb777"));
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    @DisplayName("[공지사항 수정] 관리자는 공지사항을 수정할 수 있다.")
    public void update() throws Exception {
        // given
        NoticeUpdateRequestDto dto = NoticeUpdateRequestDto.builder()
                .title("수정 공지사항")
                .content("수정 내용")
                .build();

        Notice expected = MockEntityFactory.createNotice();
        ReflectionTestUtils.setField(expected, "seq", 1);
        ReflectionTestUtils.setField(expected, "title", "수정 공지사항");
        ReflectionTestUtils.setField(expected, "content", "수정 내용");

        Mockito.when(noticeService.update(eq(1), any(NoticeUpdateRequestDto.class))).thenReturn(expected.getSeq());

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.put("/notice/1")
                .content(convertToJson(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().string("true"));
        verify(noticeService, times(1)).update(eq(1), any(NoticeUpdateRequestDto.class));
    }

    @Test
    @WithMockUser
    @DisplayName("[공지사항 조회] 사용자는 관리자가 작성한 공지사항을 조회할 수 있다.")
    public void findByNoticeSeq() throws Exception {
        // given
        MemberInfoResponseDto memberDto = new MemberInfoResponseDto();
        memberDto.convertFromEntity(MockEntityFactory.createMember());

        NoticeResponseDto expected = new NoticeResponseDto();
        expected.setSeq(1);
        expected.setTitle("공지사항");
        expected.setContent("내용");
        expected.setMember(memberDto);
        expected.setCreateDate(LocalDateTime.now().minusDays(7));

        Mockito.when(noticeService.findByNoticeSeq(eq(1))).thenReturn(expected);

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/notice/1")
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().json(convertToJson(expected)));
        verify(noticeService, times(1)).findByNoticeSeq(eq(1));
    }

    @Test
    @WithMockUser
    @DisplayName("[공지사항 전체 조회] 사용자는 관리자가 작성한 공지사항 리스트를 조회할 수 있다.")
    public void findAll() throws Exception {
        // given
        MemberInfoResponseDto memberDto = new MemberInfoResponseDto();
        memberDto.convertFromEntity(MockEntityFactory.createMember());

        NoticeListResponseDto dto = new NoticeListResponseDto();
        dto.setSeq(1);
        dto.setTitle("공지사항");
        dto.setContent("내용");
        dto.setMember(memberDto);
        dto.setCreateDate(LocalDateTime.now().minusDays(7));

        List<NoticeListResponseDto> expected = new ArrayList<>();

        Mockito.when(noticeService.findAll()).thenReturn(expected);

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/notice/list")
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().json(convertToJson(expected)));
        verify(noticeService, times(1)).findAll();
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    @DisplayName("[공지사항 삭제] 관리자는 공지사항을 삭제할 수 있다.")
    public void delete() throws Exception {
        // given

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.delete("/notice/1")
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().string("true"));
        verify(noticeService, times(1)).delete(eq(1));
    }
}