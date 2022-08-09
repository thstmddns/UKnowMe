package com.ssafy.uknowme.model.dto.BlockDto;

import com.ssafy.uknowme.web.domain.Block;
import com.ssafy.uknowme.web.domain.enums.BlockState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlockResponseDto {


    String blockedMemberNickname;
    BlockState state;

    public void convertToEntity(Block block){
        this.blockedMemberNickname = block.getBlockedMember().getNickname();
        this.state = block.getState();
    }


}
