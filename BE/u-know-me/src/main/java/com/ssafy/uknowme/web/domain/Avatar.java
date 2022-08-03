package com.ssafy.uknowme.web.domain;

import com.ssafy.uknowme.web.domain.common.BaseEntity;
import com.ssafy.uknowme.web.domain.enums.AvatarState;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
     * 서버 스토리지에 저장된 파일 이름
     */
    private String file;

    /**
     * 아바타 사용 빈도수
     */
    private int frequency;

    /**
     * 아바타 사용 여부 상태
     * AVAILABLE : 사용 가능
     * DELETE : 삭제된 상태
     */
    private AvatarState state;

    @Builder
    public Avatar(int seq, String name, String file, int frequency, AvatarState state) {
        this.seq = seq;
        this.name = name;
        this.file = file;
        this.frequency = frequency;
        this.state = state;
    }
}
