package com.ssafy.uknowme.web.controller;

import com.ssafy.uknowme.model.dto.balanceDto.BalanceListResponseDto;
import com.ssafy.uknowme.model.dto.balanceDto.BalanceResponseDto;
import com.ssafy.uknowme.model.dto.balanceDto.BalanceSaveRequestDto;
import com.ssafy.uknowme.model.dto.balanceDto.BalanceUpdateRequestDto;
import com.ssafy.uknowme.web.domain.Balance;
import com.ssafy.uknowme.web.domain.factory.MockEntityFactory;
import com.ssafy.uknowme.web.service.BalanceService;
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
@DisplayName("밸런스 컨트롤러")
public class BalanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BalanceService balanceService;

    @Test
    @WithMockUser(roles = {"MANAGER"})
    @DisplayName("[밸런스 게임 등록] 관리자는 밸런스 게임을 생성할 수 있다.")
    public void save() throws Exception {
        // given
        BalanceSaveRequestDto dto = new BalanceSaveRequestDto();
        dto.setQuestion("중국집");
        dto.setAnswer1("짬뽕");
        dto.setAnswer2("짜장면");

        Balance balance = MockEntityFactory.createBalance();

        ReflectionTestUtils.setField(balance, "seq", 1);

        Mockito.when(balanceService.save(dto)).thenReturn(balance.getSeq());

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/feature/balance/create")
                .content(convertToJson(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    @DisplayName("[밸런스 게임 수정] 관리자는 밸런스 게임을 수정할 수 있다.")
    public void update() throws Exception {
        // given
        BalanceUpdateRequestDto dto = new BalanceUpdateRequestDto();
        dto.setQuestion("중국집");
        dto.setAnswer1("부먹");
        dto.setAnswer2("찍먹");

        Balance balance = MockEntityFactory.createBalance();

        ReflectionTestUtils.setField(balance, "seq", 1);

        Mockito.when(balanceService.update(1, dto)).thenReturn(1);

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.put("/feature/balance/1")
                .content(convertToJson(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().string("true"));
    }


    @Test
    @WithMockUser
    @DisplayName("[밸런스 게임 상세 조회] 사용자는 밸런스 게임을 랜덤으로 요청할 수 있다.")
    public void findByBalanceSeq() throws Exception {
        // given
        BalanceResponseDto dto = new BalanceResponseDto();
        dto.setQuestion("중국집");
        dto.setAnswer1("짬뽕");
        dto.setAnswer2("짜장면");

        Mockito.when(balanceService.findByBalanceSeq()).thenReturn(dto);

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/feature/balance/random")
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().json(convertToJson(dto)));
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    @DisplayName("[밸런스 게임 전체 조회] 관리자는 밸런스 게임 전체를 조회할 수 있다.")
    public void findAll() throws Exception {
        // given
        List<BalanceListResponseDto> dtos = new ArrayList<>();

        BalanceListResponseDto dto = new BalanceListResponseDto();
        dto.setQuestion("중국집");
        dto.setAnswer1("짬뽕");
        dto.setAnswer2("짜장면");

        dtos.add(dto);

        Mockito.when(balanceService.findAll()).thenReturn(dtos);

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/feature/balance/list")
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().json(convertToJson(dtos)));
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    @DisplayName("[밸런스 게임 삭제] 관리자는 밸런스 게임을 삭제할 수 있다.")
    public void delete() throws Exception {
        // given

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.delete("/feature/balance/1")
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().string("true"));
        verify(balanceService, times(1)).delete(1);
    }
}
