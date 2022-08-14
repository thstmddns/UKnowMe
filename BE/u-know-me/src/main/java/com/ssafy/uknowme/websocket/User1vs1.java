package com.ssafy.uknowme.websocket;

import lombok.*;
import org.json.JSONObject;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;

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
    private List<Integer> options = new ArrayList<>(); //자신 옵션
    private List<Integer> matchingOptions = new ArrayList<>(); //상대에게 원하는 옵션

    private WebSocketSession session;

    public void convertToJSOvject(JSONObject jObject, WebSocketSession session) {


        this.seq = Integer.parseInt(jObject.getString("seq"));
        this.id = jObject.getString("id");
        this.nickname = jObject.getString("nickName");
        this.gender = jObject.getString("gender").charAt(0);
        this.maxAge = Integer.parseInt(jObject.getString("maxAge"));
        this.minAge = Integer.parseInt(jObject.getString("minAge"));
        this.age = Integer.parseInt(jObject.getString("age"));
        this.lat = Double.parseDouble(jObject.getString("lat"));
        this.lon = Double.parseDouble(jObject.getString("lon"));
       int tmp = (Integer.parseInt(jObject.getString("smoke")));
       System.out.println("tmp : "+tmp);
        this.options.add(tmp);
        this.matchingOptions.add(Integer.parseInt(jObject.getString("matchingSmoke")));
        this.session = session;
    }


}
