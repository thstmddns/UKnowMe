package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.MemberDto.*;
import com.ssafy.uknowme.web.domain.Member;
import com.ssafy.uknowme.web.domain.factory.MockEntityFactory;
import com.ssafy.uknowme.web.repository.AvatarRepository;
import com.ssafy.uknowme.web.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
@DisplayName("멤버 서비스 단위 테스트")
class MemberServiceTest {

    @InjectMocks
    private MemberServiceImpl memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private AvatarRepository avatarRepository;

    @Test
    @DisplayName("[회원가입] 사용자는 회원 가입을 할 수 있어야 한다.")
    public void join() throws Exception {
        // given
        MemberJoinRequestDto dto = new MemberJoinRequestDto();

        dto.setId("mungmnb777");
        dto.setPassword("password");
        dto.setName("이명범");
        dto.setNickname("명범짱");
        dto.setGender("M");
        dto.setBirth("19960513");
        dto.setTel("01012345678");
        dto.setSmoke("Y");
        dto.setAddress("우리집");

        Member member = MockEntityFactory.createMember();
        ReflectionTestUtils.setField(member, "seq", 1);

        Mockito.when(memberRepository.save(any(Member.class))).thenReturn(member);
        Mockito.when(avatarRepository.findById(11)).thenReturn(Optional.of(MockEntityFactory.createAvatar()));

        // when
        boolean isSuccess = memberService.join(dto);

        // then
        assertThat(isSuccess).isTrue();

        verify(memberRepository, times(1)).save(any(Member.class));
        verify(avatarRepository, times(1)).findById(11);
    }

    @Test
    @DisplayName("[회원 정보 수정] 사용자는 회원 정보를 수정할 수 있어야 한다.")
    public void update() throws Exception {
        // given
        MemberUpdateDto dto = new MemberUpdateDto();
        dto.setId("mungmnb777");
        dto.setSmoke("Y");
        dto.setAddress("우리집");

        Member member = MockEntityFactory.createMember();
        ReflectionTestUtils.setField(member, "seq", 1);

        Mockito.when(memberRepository.findById("mungmnb777")).thenReturn(Optional.of(member));

        // when
        boolean isSuccess = memberService.update(dto, "mungmnb777");

        // then
        assertThat(isSuccess).isTrue();
        verify(memberRepository, times(1)).findById("mungmnb777");
    }

    @Test
    @DisplayName("[아이디 중복 체크] 서비스는 회원 가입 시 아이디가 중복되었는지 체크해야 한다.")
    public void existsById() throws Exception {
        // given
        String memberId = "mungmnb777";

        Mockito.when(memberRepository.existsById(memberId)).thenReturn(true);

        // when
        boolean isSuccess = memberService.existsById(memberId);

        // then
        assertThat(isSuccess).isTrue();
        verify(memberRepository, times(1)).existsById(memberId);
    }

    @Test
    @DisplayName("[닉네임 중복 체크] 서비스는 회원 가입 시 닉네임이 중복되었는지 체크해야 한다.")
    public void existsByNickname() throws Exception {
        // given
        String nickname = "이명범";

        Mockito.when(memberRepository.existsByNickname(nickname)).thenReturn(true);

        // when
        boolean isSuccess = memberService.existsByNickname(nickname);

        // then
        assertThat(isSuccess).isTrue();
        verify(memberRepository, times(1)).existsByNickname(nickname);
    }

    @Test
    @DisplayName("[전화번호 중복 체크] 서비스는 회원 가입 시 전화번호가 중복되었는지 체크해야 한다.")
    public void existsByTel() throws Exception {
        // given
        String tel = "01012345678";

        Mockito.when(memberRepository.existsByTel(tel)).thenReturn(true);

        // when
        boolean isSuccess = memberService.existsByTel(tel);

        // then
        assertThat(isSuccess).isTrue();
        verify(memberRepository, times(1)).existsByTel(tel);
    }

    @Test
    @DisplayName("[회원 탈퇴] 사용자는 계정을 삭제할 수 있다.")
    public void delete() throws Exception {
        // given
        String id = "mungmnb777";

        Mockito.when(memberRepository.findById(id)).thenReturn(Optional.of(MockEntityFactory.createMember()));
        // when
        boolean isSuccess = memberService.delete(id);

        // then
        assertThat(isSuccess).isTrue();
        verify(memberRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("[회원 세부 정보 조회] 사용자는 자신의 정보를 조회할 수 있어야 한다.")
    public void getMemberInfo() throws Exception {
        // given
        String id = "mungmnb777";
        Member expectedMember = MockEntityFactory.createMember();

        Mockito.when(memberRepository.findById(id)).thenReturn(Optional.of(expectedMember));

        // when
        MemberInfoResponseDto actual = memberService.getMemberInfo(id);

        MemberInfoResponseDto expected = new MemberInfoResponseDto();
        expected.convertFromEntity(expectedMember);

        // then
        assertThat(actual).isInstanceOf(MemberInfoResponseDto.class);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        verify(memberRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("[비밀번호 검증] 사용자는 정보 수정을 위해 서버로부터 비밀번호 검증을 받을 수 있다.")
    public void validatePassword() throws Exception {
        // given
        String id = "mungmnb777";

        ValidatePasswordRequestDto dto = new ValidatePasswordRequestDto();
        dto.setPassword("password");

        Member expectedMember = MockEntityFactory.createMember();

        Mockito.when(memberRepository.findById(id)).thenReturn(Optional.of(expectedMember));

        // when
        boolean validated = memberService.validatePassword(dto, id);

        // then
        assertThat(validated).isTrue();
        verify(memberRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("[아이디 찾기] 사용자는 본인 계정의 아이디를 찾을 수 있다.")
    public void findId() throws Exception {
        // given
        FindIdRequestDto dto = new FindIdRequestDto();
        dto.setName("이명범");
        dto.setTel("01012345678");

        Member expectedMember = MockEntityFactory.createMember();

        Mockito.when(memberRepository.findByNameAndTel("이명범", "01012345678")).thenReturn(Optional.of(expectedMember));

        // when
        FindIdResponseDto responseDto = memberService.findId(dto);

        // then
        assertThat(responseDto.getId()).isEqualTo("mungmnb777");
        verify(memberRepository, times(1)).findByNameAndTel(dto.getName(), dto.getTel());
    }

    @Test
    @DisplayName("[회원 리스트 조회] 관리자는 멤버 리스트를 조회할 수 있어야 한다.")
    public void getMemberList() throws Exception {
        // given
        Mockito.when(memberRepository.findManageMemberInfoResponseDtoList(any(LocalDateTime.class))).thenReturn(anyList());

        // when
        List<ManageMemberInfoResponseDto> memberList = memberService.getMemberList();

        // then
        assertThat(memberList).isInstanceOf(List.class);
    }

    @Test
    @DisplayName("[회원 리스트 조회] 관리자는 멤버 리스트를 조회할 수 있어야 한다.")
    public void getMemberBySeq() throws Exception {
        // given
        int seq = 1;

        Mockito.when(memberRepository.findManageMemberInfoResponseDto(any(LocalDateTime.class), eq(1))).thenReturn(Optional.of(new ManageMemberInfoResponseDto()));

        // when
        ManageMemberInfoResponseDto dto = memberService.getMemberBySeq(seq);

        // then
        assertThat(dto).isInstanceOf(ManageMemberInfoResponseDto.class);
    }
}