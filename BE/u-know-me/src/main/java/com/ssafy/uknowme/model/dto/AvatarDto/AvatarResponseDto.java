package com.ssafy.uknowme.model.dto.AvatarDto;

import com.ssafy.uknowme.web.domain.Avatar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AvatarResponseDto {

    private int seq;

    private String name;

    private int frequency;

    public static AvatarResponseDto convertFromEntity(Avatar avatar) {
        return new AvatarResponseDto(avatar.getSeq(), avatar.getName(), avatar.getFrequency());
    }
}
