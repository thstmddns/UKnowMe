package com.ssafy.uknowme.web.controller;

import com.ssafy.uknowme.model.dto.KeywordListResponseDto;
import com.ssafy.uknowme.model.dto.KeywordResponseDto;
import com.ssafy.uknowme.model.dto.KeywordSaveRequestDto;
import com.ssafy.uknowme.model.dto.KeywordUpdateRequestDto;
import com.ssafy.uknowme.web.domain.Keyword;
import com.ssafy.uknowme.web.domain.factory.MockEntityFactory;
import com.ssafy.uknowme.web.service.KeywordService;
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

import java.util.ArrayList;
import java.util.List;

import static com.ssafy.uknowme.utils.JacksonUtils.convertToJson;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("키워드 컨트롤러")
class KeywordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KeywordService keywordService;

    @Test
    @WithMockUser(roles = {"MANAGER"})
    @DisplayName("[키워드 등록] 관리자는 키워드를 생성할 수 있다.")
    public void save() throws Exception {
        // given
        KeywordSaveRequestDto dto = new KeywordSaveRequestDto();
        dto.setKeyword("취미");

        Keyword expected = MockEntityFactory.createKeyword();
        ReflectionTestUtils.setField(expected, "seq", 1);

        Mockito.when(keywordService.save(dto)).thenReturn(expected.getSeq());

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/feature/keyword/create")
                .content(convertToJson(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    @DisplayName("[키워드 수정] 관리자는 키워드를 수정할 수 있다.")
    public void update() throws Exception {
        // given
        KeywordUpdateRequestDto dto = new KeywordUpdateRequestDto();
        dto.setKeyword("휴일에 뭐하나요?");

        Keyword expected = MockEntityFactory.createKeyword();
        ReflectionTestUtils.setField(expected, "seq", 1);
        ReflectionTestUtils.setField(expected, "keyword", "휴일에 뭐하나요?");

        Mockito.when(keywordService.update(1, dto)).thenReturn(expected.getSeq());

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.put("/feature/keyword/1")
                .content(convertToJson(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    @WithMockUser
    @DisplayName("[키워드 랜덤 조회] 사용자는 키워드 도우미를 통해 도움을 받을 수 있다.")
    public void findByKeywordSeq() throws Exception {
        // given
        KeywordResponseDto expected = new KeywordResponseDto(MockEntityFactory.createKeyword());

        Mockito.when(keywordService.findByKeywordSeq()).thenReturn(expected);

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/feature/keyword/random")
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().json(convertToJson(expected)));
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    @DisplayName("[키워드 전체 조회] 관리자는 전체 키워드를 조회할 수 있다.")
    public void findAll() throws Exception {
        // given
        KeywordListResponseDto dto = new KeywordListResponseDto(MockEntityFactory.createKeyword());

        List<KeywordListResponseDto> expected = new ArrayList<>();
        expected.add(dto);

        Mockito.when(keywordService.findAll()).thenReturn(expected);

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/feature/keyword/list")
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().json(convertToJson(expected)));
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    @DisplayName("[키워드 삭제] 관리자는 키워드를 삭제할 수 있다.")
    public void delete() throws Exception {
        // given

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.delete("/feature/keyword/1")
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().string("true"));
        verify(keywordService, times(1)).delete(1);
    }
}