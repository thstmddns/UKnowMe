package com.ssafy.uknowme.model.dto.AvatarDto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class AvatarSaveRequestDto {

    private String name;

    MultipartFile image;

    MultipartFile vrm;
}
