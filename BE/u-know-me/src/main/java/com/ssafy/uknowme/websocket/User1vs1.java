package com.ssafy.uknowme.websocket;

import lombok.*;
import org.json.JSONObject;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class User1vs1 {
    private int seq; // 유저 식별 번호
    private String id; // 유저 아이디
    private String nickname; //유저 닉네임
    private char gender;


    private int maxAge;

    private int minAge;

    private int age;
    private double lat; //위도
    private double lon; //경도
    private int[] options; //자신 옵션
    private int[] matchingoptions; //상대에게 원하는 옵션

    private WebSocketSession session;

    public void convertToJSOvject(JSONObject jObject, WebSocketSession session) {


        this.seq = Integer.parseInt(jObject.getString("seq"));
        this.id = jObject.getString("id");
        this.nickname = jObject.getString("nickName");
        this.gender = jObject.getString("gender").charAt(0);
        this.maxAge = Integer.parseInt(jObject.getString("maxAge"));
        this.minAge = Integer.parseInt(jObject.getString("minAge"));
        String birthYear = (jObject.getString("birth"));
        birthYear = birthYear.substring(0,4);
        LocalDate now = LocalDate.now();
        int nowYear = now.getYear();

        this.age = nowYear-Integer.parseInt(birthYear)+1;

        this.lat = Double.parseDouble(jObject.getString("lat"));
        this.lon = Double.parseDouble(jObject.getString("lon"));
        this.options =  new int[Integer.parseInt(jObject.getString("smoke"))];
        this.matchingoptions = new int[Integer.parseInt(jObject.getString("machingSmoke"))];
        this.session = session;
    }


}
