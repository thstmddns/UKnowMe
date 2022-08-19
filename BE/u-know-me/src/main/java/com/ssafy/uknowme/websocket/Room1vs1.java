package com.ssafy.uknowme.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.socket.WebSocketSession;

@Getter
@Setter
@AllArgsConstructor
@ToString

public class Room1vs1 {

    private String roomNum;
    private WebSocketSession user1Session;
    private WebSocketSession user2Session;
    private int user1Seq;
    private int user2Seq;
    private boolean user1Like;
    private boolean user2Like;
    public Room1vs1(WebSocketSession session, String roomNum, int user1Seq) {
        super();
        this.user1Session = session;
        this.user1Seq = user1Seq;
        this.roomNum = roomNum;
    }

}
