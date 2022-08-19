package com.ssafy.uknowme.web.controller;


import com.ssafy.uknowme.model.dto.ReportDto.*;
import com.ssafy.uknowme.web.service.ReportService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;



    @ApiOperation(value="신고 기능 API", notes="사용자가 신고 버튼을 눌렀을 때 사용하는 API입니다.")
    @PostMapping("/create")
    public ResponseEntity<?> createReport(@RequestBody ReportRequestDto dto) {
        if (reportService.report(dto)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value="신고 조회 API", notes="관리자가 신고 목록을 조회할 때 사용하는 API입니다.")
    @GetMapping("/read/info")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> getReport(@ModelAttribute ReportInfoRequestDto dto) {

            return new ResponseEntity<>(reportService.getReportInfo(dto), HttpStatus.OK);

    }

    @ApiOperation(value="신고 목록 조회 API", notes="관리자가 신고 목록을 조회할 때 사용하는 API입니다.")
    @GetMapping("/read/infos")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> getReportList(@ModelAttribute ReportInfosRequestDto dto) {

        return new ResponseEntity<>(reportService.getReportInfos(dto), HttpStatus.OK);

    }

    @ApiOperation(value="신고 기능 수정 API", notes="관리자가 신고 기능을 수정할 때 사용하는 API입니다.")
    @PutMapping("/modify")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> update(@RequestBody ReportUpdateRequestDto dto) {
        if (reportService.update(dto)) {
            return new ResponseEntity<>("true", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("false", HttpStatus.BAD_REQUEST);
        }
    }


    @ApiOperation(value="신고 삭제 API", notes="관리자가 신고 기능을 삭제할 때 사용하는 API입니다.")
    @DeleteMapping("/delete")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> delete(@RequestBody ReportDeleteRequestDto dto) {
        if (reportService.delete(dto)) {
            return new ResponseEntity<>("true", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("false", HttpStatus.BAD_REQUEST);
        }
    }

}
