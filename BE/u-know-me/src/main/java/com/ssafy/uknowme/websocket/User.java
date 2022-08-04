package com.ssafy.uknowme.websocket;

import lombok.*;
import org.springframework.web.socket.WebSocketSession;

@Getter
@Setter
@AllArgsConstructor
@ToString

public class User {
    private int seq; // 유저 식별 번호
    private String Id; // 유저 아이디
    private String nickname; //유저 닉네임
    private char gender;



    private int maxage;

    private int minage;

    private int age;
    private double lat; //위도
    private double lon; //경도
    private int[] options; //자신 옵션
    private int[] matchoptions; //상대에게 원하는 옵션

    private WebSocketSession session;



}
