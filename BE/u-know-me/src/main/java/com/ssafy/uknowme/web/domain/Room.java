package com.ssafy.uknowme.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Room {

    @Id @GeneratedValue
    @Column(name = "room_seq")
    private int seq;

    private int balanceCount;

    private int type;
}
