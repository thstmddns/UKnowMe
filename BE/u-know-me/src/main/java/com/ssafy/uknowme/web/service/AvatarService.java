package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.AvatarDto.AvatarResponseDto;
import com.ssafy.uknowme.model.dto.AvatarDto.AvatarSaveRequestDto;
import org.springframework.core.io.Resource;

import java.util.List;

public interface AvatarService {

    void save(AvatarSaveRequestDto avatarRequestDto);

    /** 로그인한 회원이 착용한 아바타 Vroid 모델 파일을 가져오는 메서드
     * @return Vroid 모델 파일
     */
    Resource downloadVrmFileByAuthentication();

    /**
     * 로그인한 회원이 착용한 아바타 썸네일 이미지 모델 파일을 가져오는 메서드
     * @return 아바타 이미지 파일
     */
    Resource downloadImageFileByAuthentication();

    /**
     * 아바타 seq로 아바타 Vroid 모델을 가져오는 메서드
     * @param avatarSeq
     * @return 아바타 Vroid 모델 파일
     */
    Resource downloadVrmFileBySeq(int avatarSeq);

    /**
     * 아바타 seq로 아바타 이미지를 가져오는 메서드
     * @param avatarSeq
     * @return 아바타 이미지 파일
     */
    Resource downloadImageFileBySeq(int avatarSeq);

    /**
     * 아바타 전체 리스트를 가져오는 메서드
     * @return 아바타 전체 리스트
     */
    List<AvatarResponseDto> findAllDto();
}
