package com.ssafy.uknowme.web.controller;


import com.ssafy.uknowme.model.dto.KeywordListResponseDto;
import com.ssafy.uknowme.model.dto.KeywordResponseDto;
import com.ssafy.uknowme.model.dto.KeywordSaveRequestDto;
import com.ssafy.uknowme.model.dto.KeywordUpdateRequestDto;
import com.ssafy.uknowme.web.service.KeywordServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/feature/keyword")
@RestController
@RequiredArgsConstructor
public class KeywordController {

    private final KeywordServiceImpl keywordService;

    @PostMapping("/create")
    public boolean save(@RequestBody KeywordSaveRequestDto requestDto) {
        keywordService.save(requestDto);
        return true;
    }

    @PutMapping("/{keywordSeq}")
    public boolean update(@PathVariable int keywordSeq, @RequestBody KeywordUpdateRequestDto requestDto) {
        keywordService.update(keywordSeq, requestDto);
        return true;
    }

    @GetMapping("/random")
    public KeywordResponseDto findByKeywordSeq() {
        KeywordResponseDto keywordResponseDto = keywordService.findByKeywordSeq();
        return keywordResponseDto;
    }

    @GetMapping("/list")
    public List<KeywordListResponseDto> findAll(){
        List<KeywordListResponseDto> keywords = keywordService.findAll();
        return keywords;
    }
    @DeleteMapping("/{keywordSeq}")
    public boolean delete(@PathVariable int keywordSeq) {
        keywordService.delete(keywordSeq);
        return true;
    }
}
