package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.KeywordListResponseDto;
import com.ssafy.uknowme.model.dto.KeywordResponseDto;
import com.ssafy.uknowme.model.dto.KeywordSaveRequestDto;
import com.ssafy.uknowme.model.dto.KeywordUpdateRequestDto;
import com.ssafy.uknowme.web.domain.Keyword;
import com.ssafy.uknowme.web.domain.factory.MockEntityFactory;
import com.ssafy.uknowme.web.repository.KeywordRepository;
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
@DisplayName("키워드 도우미 서비스 단위 테스트")
class KeywordServiceTest {

    @InjectMocks
    private KeywordServiceImpl keywordService;

    @Mock
    private KeywordRepository keywordRepository;

    @Test
    @DisplayName("[키워드 등록] 관리자는 키워드를 생성할 수 있다.")
    public void save() throws Exception {
        // given
        KeywordSaveRequestDto dto = new KeywordSaveRequestDto();

        dto.setKeyword("취미");

        Keyword expected = MockEntityFactory.createKeyword();

        Mockito.when(keywordRepository.save(any(Keyword.class)))
                .thenAnswer((Answer<Keyword>) invocation -> {
                    ReflectionTestUtils.setField(expected, "seq", 1);
                    return expected;
                });

        // when
        Integer seq = keywordService.save(dto);

        // then
        assertThat(seq).isEqualTo(1);

        verify(keywordRepository, times(1)).save(any(Keyword.class));
    }

    @Test
    @DisplayName("[키워드 수정] 관리자는 키워드를 수정할 수 있다.")
    public void update() throws Exception {
        // given
        KeywordUpdateRequestDto dto = new KeywordUpdateRequestDto();

        dto.setKeyword("취미");

        Mockito.when(keywordRepository.findBySeq(eq(1)))
                .thenAnswer((Answer<Optional<Keyword>>) invocation -> {
                    Keyword expected = MockEntityFactory.createKeyword();
                    ReflectionTestUtils.setField(expected, "seq", 1);
                    return Optional.of(expected);
                });

        // when
        Integer seq = keywordService.update(1, dto);

        // then
        assertThat(seq).isEqualTo(1);

        verify(keywordRepository, times(1)).findBySeq(eq(1));
    }

    @Test
    @DisplayName("[키워드 랜덤 조회] 사용자는 키워드 도우미를 통해 도움을 받을 수 있다.")
    public void findByKeywordSeq() throws Exception {
        // given
        Mockito.when(keywordRepository.findAll())
                .thenAnswer((Answer<List<Keyword>>) invocation -> {
                    Keyword keyword = MockEntityFactory.createKeyword();

                    ReflectionTestUtils.setField(keyword, "seq", 1);

                    List<Keyword> expected = new ArrayList<>();
                    expected.add(keyword);

                    return expected;
                });

        // when
        KeywordResponseDto dto = keywordService.findByKeywordSeq();

        // then
        assertThat(dto.getSeq()).isEqualTo(1);
        verify(keywordRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("[키워드 전체 조회] 관리자는 전체 키워드를 조회할 수 있다.")
    public void findAll() throws Exception {
        // given
        Mockito.when(keywordRepository.findAll())
                .thenAnswer((Answer<List<Keyword>>) invocation -> {
                    Keyword keyword = MockEntityFactory.createKeyword();

                    ReflectionTestUtils.setField(keyword, "seq", 1);

                    List<Keyword> expected = new ArrayList<>();
                    expected.add(keyword);

                    return expected;
                });

        // when
        List<KeywordListResponseDto> dto = keywordService.findAll();

        // then
        assertThat(dto).isInstanceOf(List.class);
        verify(keywordRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("[키워드 삭제] 관리자는 키워드를 삭제할 수 있다.")
    public void delete() throws Exception {
        // given
        Keyword expected = MockEntityFactory.createKeyword();

        ReflectionTestUtils.setField(expected, "seq", 1);

        Mockito.when(keywordRepository.findBySeq(1)).thenReturn(Optional.of(expected));

        // when
        keywordService.delete(1);

        // then
        verify(keywordRepository, times(1)).findBySeq(1);
        verify(keywordRepository, times(1)).delete(expected);
    }
}