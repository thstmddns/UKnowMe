package com.ssafy.uknowme.web.controller;


import com.ssafy.uknowme.model.dto.KeywordListResponseDto;
import com.ssafy.uknowme.model.dto.KeywordResponseDto;
import com.ssafy.uknowme.model.dto.KeywordSaveRequestDto;
import com.ssafy.uknowme.model.dto.KeywordUpdateRequestDto;
import com.ssafy.uknowme.web.service.KeywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/feature/keyword")
@RestController
@RequiredArgsConstructor
public class KeywordController {

    private final KeywordService keywordService;

    @PostMapping("/create")
    @Secured("ROLE_MANAGER")
    public boolean save(@RequestBody KeywordSaveRequestDto requestDto) {
        keywordService.save(requestDto);
        return true;
    }

    @PutMapping("/{keywordSeq}")
    @Secured("ROLE_MANAGER")
    public boolean update(@PathVariable int keywordSeq, @RequestBody KeywordUpdateRequestDto requestDto) {
        keywordService.update(keywordSeq, requestDto);
        return true;
    }

    @GetMapping("/random")
    public KeywordResponseDto findByKeywordSeq() {
        return keywordService.findByKeywordSeq();
    }

    @GetMapping("/list")
    @Secured("ROLE_MANAGER")
    public List<KeywordListResponseDto> findAll(){
        return keywordService.findAll();
    }
    @DeleteMapping("/{keywordSeq}")
    @Secured("ROLE_MANAGER")
    public boolean delete(@PathVariable int keywordSeq) {
        keywordService.delete(keywordSeq);
        return true;
    }
}
