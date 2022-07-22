package com.ssafy.uknowme.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Balance {

    @Id @GeneratedValue
    @Column(name = "balance_seq")
    private int seq;

    private String question;

    private String answer1;

    private String answer2;
}
