package com.ssafy.uknowme.websocket;

import lombok.*;
import org.json.JSONObject;
import org.springframework.web.socket.WebSocketSession;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UserInfo {

    WebSocketSession session;
    int seq;
    boolean like;


    public UserInfo(int seq, WebSocketSession session) {
        this.session = session;
        this.seq = seq;
        this.like = false;
    }
}



