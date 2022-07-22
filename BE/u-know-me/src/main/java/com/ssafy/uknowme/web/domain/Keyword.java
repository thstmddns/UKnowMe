package com.ssafy.uknowme.web.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Keyword {

    @Id @GeneratedValue
    @Column(name = "keyword_seq")
    private int seq;

    private String keyword;
}
