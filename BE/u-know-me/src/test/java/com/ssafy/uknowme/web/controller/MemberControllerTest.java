package com.ssafy.uknowme.web.controller;

import com.ssafy.uknowme.model.dto.MemberDto.*;
import com.ssafy.uknowme.web.domain.enums.ReportState;
import com.ssafy.uknowme.web.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
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

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("멤버 컨트롤러")
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Test
    @WithAnonymousUser
    @DisplayName("[회원가입] 사용자는 회원 가입을 할 수 있어야 한다.")
    public void joinTest() throws Exception {
        // given
        MemberJoinRequestDto dto = new MemberJoinRequestDto();

        dto.setId("mungmnb777");
        dto.setPassword("asd12345!");
        dto.setName("이명범");
        dto.setNickname("명범짱");
        dto.setGender("M");
        dto.setBirth("19960513");
        dto.setTel("01012345678");
        dto.setSmoke("Y");
        dto.setAddress("우리집");

        Mockito.when(memberService.join(any(MemberJoinRequestDto.class))).thenReturn(true);

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/member/join")
                .content(convertToJson(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("[아이디 중복 체크] 서비스는 회원 가입 시 아이디가 중복되었는지 체크해야 한다.")
    public void duplicateIdTest() throws Exception {
        // given
        DuplicatedIdRequestDto dto = new DuplicatedIdRequestDto();

        dto.setId("mungmnb777");

        Mockito.when(memberService.existsById(dto.getId())).thenReturn(true);

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/member/check/id?id=mungmnb777")
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().string("false"));
    }

    @Test
    @WithAnonymousUser
    @DisplayName("[닉네임 중복 체크] 서비스는 회원 가입 시 닉네임이 중복되었는지 체크해야 한다.")
    public void duplicateNicknameTest() throws Exception {
        // given
        DuplicatedNicknameRequestDto dto = new DuplicatedNicknameRequestDto();

        dto.setNickname("명범짱");

        Mockito.when(memberService.existsByNickname(dto.getNickname())).thenReturn(true);

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/member/check/nickname?nickname=명범짱")
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().string("false"));
    }

    @Test
    @WithMockUser(username = "mungmnb777")
    @DisplayName("[회원 정보 수정] 사용자는 회원 정보를 수정할 수 있어야 한다.")
    public void updateTest() throws Exception {
        // given
        MemberUpdateDto dto = new MemberUpdateDto();
        dto.setId("mungmnb777");
        dto.setSmoke("Y");
        dto.setAddress("우리집");
        
        Mockito.when(memberService.update(any(MemberUpdateDto.class), eq("mungmnb777"))).thenReturn(true);

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.put("/member/update")
                .content(convertToJson(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    @WithMockUser("mungmnb777")
    @DisplayName("[착용 아바타 변경] 사용자는 아바타를 변경할 수 있어야 한다.")
    public void changeAvatarTest() throws Exception {
        // given
        ChangeAvatarDto dto = new ChangeAvatarDto();
        dto.setAvatarSeq(1);

        Mockito.when(memberService.update(any(MemberUpdateDto.class), eq("mungmnb777"))).thenReturn(true);

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.put("/member/avatar")
                .content(convertToJson(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    @WithMockUser
    @DisplayName("[비밀번호 변경] 사용자는 비밀번호를 변경할 수 있어야 한다.")
    public void changePasswordTest() throws Exception {
        // given
        ChangePasswordDto dto = new ChangePasswordDto();
        dto.setId("mungmnb777");
        dto.setChangePassword("asd12345");

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.put("/member/password")
                .content(convertToJson(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    @WithMockUser(username = "mungmnb777")
    @DisplayName("[회원 세부 정보 조회] 사용자는 자신의 정보를 조회할 수 있어야 한다.")
    public void getMemberInfoTest() throws Exception {
        // given
        Mockito.when(memberService.getMemberInfo(eq("mungmnb777"))).thenReturn(new MemberInfoResponseDto());

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/member")
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().json(convertToJson(new MemberInfoResponseDto())));
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    @DisplayName("[회원 리스트 조회] 관리자는 멤버 리스트를 조회할 수 있어야 한다.")
    public void getMemberListTest() throws Exception {
        // given
        List<ManageMemberInfoResponseDto> list = new ArrayList<>();
        list.add(new ManageMemberInfoResponseDto(1, "mungmnb777", "이명범", "명범짱", "M", "19960513", "01012345678", "Y", "우리집", ReportState.CANCEL,0L, LocalDateTime.now() , LocalDateTime.now()));

        Mockito.when(memberService.getMemberList()).thenReturn(list);

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/member/list")
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().json(convertToJson(list)));
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    @DisplayName("[특정 멤버 세부 정보 조회] 관리자는 특정 멤버의 세부 정보를 조회할 수 있어야 한다.")
    public void getMemberBySeqTest() throws Exception {
        // given
        ManageMemberInfoResponseDto dto = new ManageMemberInfoResponseDto(1, "mungmnb777", "이명범", "명범짱", "M", "19960513", "01012345678", "Y", "우리집", ReportState.CANCEL, 0L, LocalDateTime.now(), LocalDateTime.now());
        Mockito.when(memberService.getMemberBySeq(1)).thenReturn(dto);

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/member/1")
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().json(convertToJson(dto)));
    }

    @Test
    @WithMockUser(username = "mungmnb777")
    @DisplayName("[비밀번호 검증] 사용자는 정보 수정을 위해 서버로부터 비밀번호 검증을 받을 수 있다.")
    public void validatePasswordTest() throws Exception {
        // given
        ValidatePasswordRequestDto dto = new ValidatePasswordRequestDto();
        dto.setPassword("password");

        Mockito.when(memberService.validatePassword(any(ValidatePasswordRequestDto.class), eq("mungmnb777"))).thenReturn(true);

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/member/validate/password")
                .content(convertToJson(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    @WithMockUser(username = "mungmnb777")
    @DisplayName("[회원 탈퇴] 사용자는 계정을 삭제할 수 있다.")
    public void deleteTest() throws Exception {
        // given
        String id = "mungmnb777";
        Mockito.when(memberService.delete(id)).thenReturn(true);

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.delete("/member")
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    @WithMockUser
    @DisplayName("[아이디 찾기] 사용자는 본인 계정의 아이디를 찾을 수 있다.")
    public void findIdTest() throws Exception {
        // given
        FindIdResponseDto dto = new FindIdResponseDto();
        dto.setId("mungmnb777");
        
        Mockito.when(memberService.findId(any(FindIdRequestDto.class))).thenReturn(dto);

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/member/find/id?name=이명범&tel=01012345678")
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.content().json(convertToJson(dto)));
    }


}