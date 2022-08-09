package com.ssafy.uknowme.web.controller;


import com.ssafy.uknowme.model.dto.BlockDto.BlockInfoRequestDto;
import com.ssafy.uknowme.model.dto.BlockDto.BlockRequestDto;
import com.ssafy.uknowme.model.dto.ReportDto.ReportInfoRequestDto;
import com.ssafy.uknowme.model.dto.ReportDto.ReportUpdateRequestDto;
import com.ssafy.uknowme.web.service.BlockService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/block")
public class BlockController {

        private final BlockService blockService;


    @ApiOperation(value="차단 기능 API", notes="사용자가 차단 버튼을 눌렀을 때 사용하는 API입니다.")
    @PostMapping("/create")
    public ResponseEntity<?> createBlock(@RequestBody BlockRequestDto dto) {
        if (blockService.block(dto)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value="차단 목록 조회 API", notes="차단 목록을 조회할 때 사용하는 API입니다.")
    @GetMapping("/read/info")
    public ResponseEntity<?> getReport(@ModelAttribute BlockInfoRequestDto dto) {

        return new ResponseEntity<>(blockService.getBlockInfos(dto), HttpStatus.OK);

    }
    @ApiOperation(value="차단 수정 API", notes="차단 기능을 수정할 때 사용하는 API입니다.")
    @PutMapping("/modify")
    public ResponseEntity<?> update(@RequestBody BlockRequestDto dto) {
        if (blockService.update(dto)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }






}
