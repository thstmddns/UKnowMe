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
public class Balance extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "balance_seq")
    private int seq;

    /**
     * 밸런스 게임 질문
     */
    private String question;

    /**
     * 해당 질문에 대한 첫 번째 보기
     */
    private String answer1;

    /**
     * 해당 질문에 대한 두 번째 보기
     */
    private String answer2;

    @Builder
    public Balance(int seq, String question, String answer1, String answer2) {
        this.seq = seq;
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
    }

    public void update(String question, String answer1, String answer2) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2= answer2;
    }
}
