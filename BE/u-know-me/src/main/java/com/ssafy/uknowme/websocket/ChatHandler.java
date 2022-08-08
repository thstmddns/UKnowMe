package com.ssafy.uknowme.websocket;

import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


import java.io.IOException;
import java.util.*;

@CrossOrigin
@Component
@Log4j2
public class ChatHandler extends TextWebSocketHandler {
    private static List<Room1vs1> room1vs1 = new ArrayList();
    private static List<WebSocketSession> list = new ArrayList<>();

    final TextMessage UKNOWME = new TextMessage("{\n" +
            "        \"key\" : \"uknowme\",\n" +
            "    }");

    final TextMessage ROOM_BREAK = new TextMessage("{\n" +
            "        \"key\" : \"roomBreak\",\n" +
            "    }");

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        JSONObject jObject = new JSONObject(payload);
        String key = jObject.get("key").toString();
        switch (key) {
            case "chat_start_1":
                //방있으면 그 안에 들어가기
                for (int roomcnt = 0; roomcnt < room1vs1.size(); roomcnt++) {

                    if (room1vs1.get(roomcnt).getRoomNum().equals(jObject.get("room_seq"))) {
                        room1vs1.get(roomcnt).setUser2Session(session);
                        return;
                    }
                }
                //방 없으면 새로 만들기
                room1vs1.add(new Room1vs1(session, jObject.get("room_seq").toString()));
                break;

            case "heart_1":
                System.out.println("하트눌림");
                for (int roomcnt = 0; roomcnt < room1vs1.size(); roomcnt++) {
                    try {
                        sendLike(roomcnt, session, UKNOWME);
                    } catch (IndexOutOfBoundsException e) {
                        log.info("리스트를 벗어나 버렸습니다.");
                    }
                }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        list.add(session);

        System.out.println(list.toString());

        log.info(session + " 클라이언트 접속" + list.toString());
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {


        log.info(session + " 클라이언트 접속 해제");
        for (int roomcnt = 0; roomcnt < room1vs1.size(); roomcnt++) {

            if (room1vs1.get(roomcnt).getUser1Session().equals(session) |
                    room1vs1.get(roomcnt).getUser2Session().equals(session)) {
                try {
                    sendMessage(roomcnt, ROOM_BREAK);
                } catch (IllegalStateException e) {
                    log.info("이미 나간 사람에게는 메세지가 가지 않습니다");
                }
                room1vs1.remove(roomcnt);
                System.out.println("방ㅂㅂ " + room1vs1.toString());
            }
        }
        list.remove(session);
    }

    public void sendLike(int roomcnt, WebSocketSession session, TextMessage msg) throws IOException {
        if (session.equals(room1vs1.get(roomcnt).getUser1Session())) {
            room1vs1.get(roomcnt).setUser1Like(true);
            if (room1vs1.get(roomcnt).isUser1Like() == true) {
                room1vs1.get(roomcnt).getUser1Session().sendMessage(msg);
                room1vs1.get(roomcnt).getUser2Session().sendMessage(msg);
                return;
            }
        } else {
            room1vs1.get(roomcnt).setUser2Like(true);
            if (room1vs1.get(roomcnt).isUser2Like() == true) {
                room1vs1.get(roomcnt).getUser1Session().sendMessage(msg);
                room1vs1.get(roomcnt).getUser2Session().sendMessage(msg);
                return;
            }
        }
    }

    public void sendMessage(int roomcnt, TextMessage msg) throws IOException, IllegalStateException {
        room1vs1.get(roomcnt).getUser1Session().sendMessage(msg);
        room1vs1.get(roomcnt).getUser2Session().sendMessage(msg);
    }
}
