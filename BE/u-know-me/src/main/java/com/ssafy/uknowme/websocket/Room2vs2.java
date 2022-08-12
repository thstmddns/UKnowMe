package com.ssafy.uknowme.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString

public class Room2vs2 {


    private String roomNum;

    private  List<UserInfo> userInfos = new ArrayList<>();


    public Room2vs2(WebSocketSession session, String roomNum, int user1Seq) {
        super();
        this.userInfos.add(new UserInfo(user1Seq,session));
        this.roomNum = roomNum;
    }

    public void addlike(int userCnt){
        this.userInfos.get(userCnt).like = true;
    }

    public void addUserInfos(int userSeq, WebSocketSession session){
        this.userInfos.add(new UserInfo(userSeq,session));
    }


}
