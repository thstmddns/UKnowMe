package com.ssafy.uknowme.web.controller;


import com.ssafy.uknowme.model.dto.keywordDto.KeywordSaveRequestDto;
import com.ssafy.uknowme.model.dto.keywordDto.KeywordUpdateRequestDto;
import com.ssafy.uknowme.web.domain.Keyword;
import com.ssafy.uknowme.web.repository.KeywordRepository;
import com.ssafy.uknowme.web.service.keyword.KeywordServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/feature/keyword")
@RestController
@RequiredArgsConstructor
public class KeywordController {

    private final KeywordServiceImpl keywordService;

    @Autowired
    private KeywordRepository keywordRepository;

    @PostMapping("/create")
    public  boolean save(@RequestBody KeywordSaveRequestDto requestDto) {
        keywordService.save(requestDto);
        return true;
    }

    @PutMapping("/{keywordSeq}")
    public boolean update(@PathVariable int keywordSeq, @RequestBody KeywordUpdateRequestDto requestDto) {
        keywordService.update(keywordSeq, requestDto);
        return true;
    }

    @GetMapping("/random")
    public boolean findByKeywordSeq (@PathVariable int keywordSeq) {
        keywordService.findByKeywordSeq(keywordSeq);
        return true;
    }

    @GetMapping("/list")
    public boolean keyword(Model model) {
        List<Keyword> keywords = keywordRepository.findAll();
        model.addAttribute("keyword", keywords);
        return true;
    }

    @DeleteMapping("/{keywordSeq}")
    public boolean delete(@PathVariable int keywordSeq) {
        keywordService.delete(keywordSeq);
        return true;
    }

}
