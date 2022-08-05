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
    private boolean user1Like;
    private boolean user2Like;
    public Room1vs1(WebSocketSession session, String roomNum) {
        super();
        this.user1Session = session;
        this.roomNum = roomNum;
    }

}
