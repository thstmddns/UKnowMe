package com.ssafy.uknowme.web.service;


import com.ssafy.uknowme.model.dto.BlockDto.BlockInfoRequestDto;
import com.ssafy.uknowme.model.dto.BlockDto.BlockRequestDto;
import com.ssafy.uknowme.model.dto.BlockDto.BlockResponseDto;
import com.ssafy.uknowme.model.dto.ReportDto.*;

import java.util.List;

public interface BlockService {
    boolean block(BlockRequestDto dto);
    List<BlockResponseDto> getBlockInfos(BlockInfoRequestDto dto);

    boolean update(BlockRequestDto dto);

}
