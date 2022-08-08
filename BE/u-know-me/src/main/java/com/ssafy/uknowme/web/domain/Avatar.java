package com.ssafy.uknowme.web.domain;

import com.ssafy.uknowme.web.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Avatar extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "avatar_seq")
    private int seq;

    /**
     * 아바타 이름 ex) 유노, 
     */
    private String name;

    /**
     * 서버 스토리지에 저장된 썸네일 이미지 이름
     */
    private String image;

    /**
     * 서버 스토리지에 저장된 vroid 모델 파일 이름
     */
    private String vrm;

    /**
     * 아바타 사용 빈도수
     */
    private int frequency;

    @Builder
    public Avatar(int seq, String name, String image, String vrm, int frequency) {
        this.seq = seq;
        this.name = name;
        this.image = image;
        this.vrm = vrm;
        this.frequency = frequency;
    }
}
