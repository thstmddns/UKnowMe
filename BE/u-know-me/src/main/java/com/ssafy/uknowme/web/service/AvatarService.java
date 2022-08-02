package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.AvatarRequestDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface AvatarService {

    // 세션 생성 시 아바타를 다운로드 받아서 제공해주는 메서드
    Resource downloadAvatar();

    void save(AvatarRequestDto avatarRequestDto, MultipartFile file);
}
