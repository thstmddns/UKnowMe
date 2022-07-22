package com.ssafy.uknowme.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Notice {

    @Id @GeneratedValue
    @Column(name = "member_seq")
    private int seq;

    private String title;

    private String content;

    private int hit;
}
