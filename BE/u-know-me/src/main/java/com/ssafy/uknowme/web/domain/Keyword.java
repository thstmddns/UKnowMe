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
public class Keyword extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "keyword_seq")
    private int seq;

    /**
     * 제공될 키워드
     */
    private String keyword;

    @Builder
    public Keyword(int seq, String keyword) {
        this.seq = seq;
        this.keyword = keyword;
    }

    public void update(String keyword) {
        this.keyword = keyword;
    }
}
