package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.balanceDto.BalanceListResponseDto;
import com.ssafy.uknowme.model.dto.balanceDto.BalanceResponseDto;
import com.ssafy.uknowme.model.dto.balanceDto.BalanceSaveRequestDto;
import com.ssafy.uknowme.model.dto.balanceDto.BalanceUpdateRequestDto;
import com.ssafy.uknowme.web.domain.Balance;
import com.ssafy.uknowme.web.domain.factory.MockEntityFactory;
import com.ssafy.uknowme.web.repository.BalanceRepository;
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
@DisplayName("밸런스 게임 서비스 단위 테스트")
class BalanceServiceTest {

    @InjectMocks
    private BalanceServiceImpl balanceService;

    @Mock
    private BalanceRepository balanceRepository;

    @Test
    @DisplayName("[밸런스 게임 등록] 관리자는 밸런스 게임을 생성할 수 있다.")
    public void join() throws Exception {
        // given
        BalanceSaveRequestDto dto = new BalanceSaveRequestDto();

        dto.setQuestion("중국집");
        dto.setAnswer1("짬뽕");
        dto.setAnswer2("짜장면");

        Balance expected = MockEntityFactory.createBalance();

        Mockito.when(balanceRepository.save(any(Balance.class)))
                .thenAnswer((Answer<Balance>) invocation -> {
                    ReflectionTestUtils.setField(expected, "seq", 1);
                    return expected;
                });

        // when
        Integer seq = balanceService.save(dto);

        // then
        assertThat(seq).isEqualTo(1);

        verify(balanceRepository, times(1)).save(any(Balance.class));
    }

    @Test
    @DisplayName("[밸런스 게임 수정] 관리자는 밸런스 게임을 수정할 수 있다.")
    public void update() throws Exception {
        // given
        BalanceUpdateRequestDto dto = new BalanceUpdateRequestDto();

        dto.setQuestion("중국집");
        dto.setAnswer1("찍먹");
        dto.setAnswer2("부먹");

        Mockito.when(balanceRepository.findBySeq(eq(1)))
                .thenAnswer((Answer<Optional<Balance>>) invocation -> {
                    Balance expected = MockEntityFactory.createBalance();
                    ReflectionTestUtils.setField(expected, "seq", 1);
                    return Optional.of(expected);
                });

        // when
        Integer seq = balanceService.update(1, dto);

        // then
        assertThat(seq).isEqualTo(1);

        verify(balanceRepository, times(1)).findBySeq(eq(1));
    }

    @Test
    @DisplayName("[밸런스 게임 상세 조회] 사용자는 밸런스 게임을 랜덤으로 요청할 수 있다.")
    public void findByBalanceSeq() throws Exception {
        // given
        Mockito.when(balanceRepository.findAll())
                .thenAnswer((Answer<List<Balance>>) invocation -> {
                    Balance balance = MockEntityFactory.createBalance();

                    ReflectionTestUtils.setField(balance, "seq", 1);

                    List<Balance> expected = new ArrayList<>();
                    expected.add(balance);

                    return expected;
                });

        // when
        BalanceResponseDto dto = balanceService.findByBalanceSeq();

        // then
        assertThat(dto.getSeq()).isEqualTo(1);
        verify(balanceRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("[밸런스 게임 전체 조회] 관리자는 밸런스 게임 전체를 조회할 수 있다.")
    public void findAll() throws Exception {
        // given
        Mockito.when(balanceRepository.findAll())
                .thenAnswer((Answer<List<Balance>>) invocation -> {
                    Balance balance = MockEntityFactory.createBalance();

                    ReflectionTestUtils.setField(balance, "seq", 1);

                    List<Balance> expected = new ArrayList<>();
                    expected.add(balance);

                    return expected;
                });

        // when
        List<BalanceListResponseDto> dto = balanceService.findAll();

        // then
        assertThat(dto).isInstanceOf(List.class);
        verify(balanceRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("[밸런스 게임 삭제] 관리자는 밸런스 게임을 삭제할 수 있다.")
    public void delete() throws Exception {
        // given
        Balance expected = MockEntityFactory.createBalance();

        ReflectionTestUtils.setField(expected, "seq", 1);

        Mockito.when(balanceRepository.findBySeq(1)).thenReturn(Optional.of(expected));

        // when
        balanceService.delete(1);

        // then
        verify(balanceRepository, times(1)).findBySeq(1);
        verify(balanceRepository, times(1)).delete(expected);
    }
}