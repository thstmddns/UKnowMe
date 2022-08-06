package com.ssafy.uknowme.websocket;

import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.xml.soap.Text;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

                    if (room1vs1.get(roomcnt).getRoomNum().equals(jObject.get("room_seq").toString())) {
                        room1vs1.get(roomcnt).setUser2Session(session);
                        return;
                    }
                }
                //방 없으면 새로 만들기
                room1vs1.add(new Room1vs1(session, jObject.get("room_seq").toString()));
                break;

            case "heart_1":
                log.info("하트 버튼 클릭 요청");
                for (int roomcnt = 0; roomcnt < room1vs1.size(); roomcnt++) {
                    try {
                        sendLike(roomcnt, session, UKNOWME);
                    } catch (IndexOutOfBoundsException e ) {
                        log.info("리스트를 벗어나 버렸습니다.");
                    }catch (IllegalStateException e){
                        log.info("웹소켓이 이미 닫혔습니다");
                    }
                }
                break;
            case "balance_q_request_1":
                log.info("밸런스 게임 요청");
                String balance_q_request_tmp = String.format("{\n" +
                                "\t\"key\" : \"balance_q_response_1\",\n" +
                                "\t\"question\" :\"%s\",\n" +
                                "\t\"answer1\" : \"%s\",\n" +
                                "\t\"answer2\" : \"%s\"\n" +
                                "}", jObject.get("question").toString(),
                        jObject.get("answer1").toString(),
                        jObject.get("answer2").toString());
                TextMessage balance_q_request_msg = new TextMessage(balance_q_request_tmp);
                for (int roomcnt = 0; roomcnt < room1vs1.size(); roomcnt++) {
                    try {
                        sendUserMessage(roomcnt, session, balance_q_request_msg);
                    } catch (IndexOutOfBoundsException e) {
                        log.info("리스트를 벗어나 버렸습니다.");
                    }catch (IllegalStateException e){
                        log.info("웹소켓이 이미 닫혔습니다");
                    }
                }
                break;
            case "balance_a_request_1":
                log.info("밸런스 게임 답 요청");
                String balance_a_request_tmp = String.format("{\n" +
                        "\t\"key\" : \"balance_q_response_1\",\n" +
                        "\t\"answer\" : \"%s\",\n" +
                        "}", jObject.get("answser").toString());
                TextMessage balance_a_request_msg = new TextMessage(balance_a_request_tmp);

                for (int roomcnt = 0; roomcnt < room1vs1.size(); roomcnt++) {
                    try {
                        sendUserMessage(roomcnt, session, balance_a_request_msg);
                    } catch (IndexOutOfBoundsException e) {
                        log.info("리스트를 벗어나 버렸습니다.");
                    }catch (IllegalStateException e){
                        log.info("웹소켓이 이미 닫혔습니다");
                    }

                }
            case "keyword_helper_request_1":
                log.info("키워드 헬퍼 요청");
                String keyword_helper_tmp = String.format("{\n" +
                        "\t\"key\" : \"balance_q_response_1\",\n" +
                        "\t\"keyword\" : \"%s\",\n" +
                        "}", jObject.get("keyword").toString());
                TextMessage keyword_helper_response_msg = new TextMessage(keyword_helper_tmp);
                for (int roomcnt = 0; roomcnt < room1vs1.size(); roomcnt++) {
                    try {

                        sendUserMessage(roomcnt, session, keyword_helper_response_msg);
                    } catch (IndexOutOfBoundsException e) {
                        log.info("리스트를 벗어나 버렸습니다.");
                    }catch (IllegalStateException e){
                        log.info("웹소켓이 이미 닫혔습니다");
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
            try {
                if (room1vs1.get(roomcnt).getUser1Session().equals(session) |
                        room1vs1.get(roomcnt).getUser2Session().equals(session)) {
                    try {
                        sendRoomMessage(roomcnt, ROOM_BREAK);
                    } catch (IllegalStateException e) {
                        log.info("이미 나간 사람에게는 메세지가 가지 않습니다");
                    }
                    room1vs1.remove(roomcnt);
                    log.info("방ㅂㅂ " + room1vs1.toString());
                }
            } catch (NullPointerException e) {
                log.info("한사람한테만 보냈습니다");
            }
        }
    }

    public void sendLike(int roomcnt, WebSocketSession session, TextMessage msg) throws IOException {
        System.out.println("하트눌림0");
        if (session.equals(room1vs1.get(roomcnt).getUser1Session())) {
            room1vs1.get(roomcnt).setUser1Like(true);
            if (room1vs1.get(roomcnt).isUser2Like() == true) {
                System.out.println("하트눌림1");
                room1vs1.get(roomcnt).getUser1Session().sendMessage(msg);
                room1vs1.get(roomcnt).getUser2Session().sendMessage(msg);
                return;
            }
        } else {
            room1vs1.get(roomcnt).setUser2Like(true);
            if (room1vs1.get(roomcnt).isUser1Like() == true) {
                System.out.println("하트눌림2");
                room1vs1.get(roomcnt).getUser1Session().sendMessage(msg);
                room1vs1.get(roomcnt).getUser2Session().sendMessage(msg);
                return;
            }
        }
    }

    public void sendRoomMessage(int roomcnt, TextMessage msg) throws IOException, IllegalStateException {
        room1vs1.get(roomcnt).getUser1Session().sendMessage(msg);
        room1vs1.get(roomcnt).getUser2Session().sendMessage(msg);
    }


    public void sendUserMessage(int roomcnt, WebSocketSession session, TextMessage msg) throws IOException, IllegalStateException {
        if (session == room1vs1.get(roomcnt).getUser1Session()) { //자기가1이면
            room1vs1.get(roomcnt).getUser2Session().sendMessage(msg); //2한테 메세지 보내기
        } else {//자기가 2라면
            room1vs1.get(roomcnt).getUser1Session().sendMessage(msg); //1한테 메세지 보내기
        }
    }
}
