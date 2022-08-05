package com.ssafy.uknowme.web.domain;

import com.ssafy.uknowme.web.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class    Notice extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "notice_seq")
    private int seq;

    /**
     * 공지사항 제목
     */
    private String title;

    /**
     * 공지사항 내용
     */
    private String content;

    /**
     * 공지사항 작성자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_seq")
    private Member member;

    private int hit;

    @Builder
    public Notice(int seq, String title, String content, Member member, int hit) {
        this.seq = seq;
        this.title = title;
        this.content = content;
        this.member = member;
        this.hit = hit;
    }
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
