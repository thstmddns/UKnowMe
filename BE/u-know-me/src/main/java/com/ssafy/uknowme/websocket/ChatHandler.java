package com.ssafy.uknowme.websocket;

import com.ssafy.uknowme.model.dto.balanceDto.BalanceResponseDto;
import com.ssafy.uknowme.web.service.BalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Component
@Log4j2
@Controller
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {
    private static List<Room1vs1> room1vs1 = new ArrayList();
    private static List<Room2vs2> room2vs2 = new ArrayList();
    private static List<WebSocketSession> list = new ArrayList<>();

    static final int PEOPLE_NUM_2VS2 = 4;

    final TextMessage UKNOWME = new TextMessage("{\n" +
            "        \"key\" : \"uknowme\"\n" +
            "    }");

    final TextMessage ROOM_BREAK = new TextMessage("{\n" +
            "        \"key\" : \"roomBreak\",\n" +
            "    }");

    private final BalanceService balanceService;

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
                        room1vs1.get(roomcnt).setUser2Seq(Integer.parseInt(jObject.get("user_seq").toString()));
                        return;
                    }
                }
                //방 없으면 새로 만들기
                room1vs1.add(new Room1vs1(session, jObject.get("room_seq").toString(), Integer.parseInt(jObject.get("user_seq").toString())));
                break;
            case "chat_start_2":
                //방있으면 그 안에 들어가기
                log.info("2대2룸 입장");
                for (int roomcnt = 0; roomcnt < room2vs2.size(); roomcnt++) {
                    if (room2vs2.get(roomcnt).getRoomNum().equals(jObject.get("room_seq").toString())) {
                        room2vs2.get(roomcnt).addUserInfos(Integer.parseInt(jObject.get("user_seq").toString()), session);
                        return;
                    }
                }
                //방 없으면 새로 만들기
                room2vs2.add(new Room2vs2(session, jObject.get("room_seq").toString(), Integer.parseInt(jObject.get("user_seq").toString())));
                break;
            case "heart_1":
                log.info("하트 버튼 클릭 요청");
                for (int roomcnt = 0; roomcnt < room1vs1.size(); roomcnt++) {
                    try {
                        sendLike(roomcnt, session, UKNOWME);
                    } catch (IndexOutOfBoundsException e) {
                        log.info("리스트를 벗어나 버렸습니다.");
                    } catch (IllegalStateException e) {
                        log.info("웹소켓이 이미 닫혔습니다");
                    }
                }
                break;
            case "heart_2":
                log.info("하트 버튼 클릭 요청");
                String roomNum = jObject.get("room").toString();
                try {
                    sendLike2(roomNum, session, UKNOWME);
                } catch (IndexOutOfBoundsException e) {
                    log.info("리스트를 벗어나 버렸습니다.");
                } catch (IllegalStateException e) {
                    log.info("웹소켓이 이미 닫혔습니다");
                }

                break;
            case "balance_q_request_1":
                log.info("밸런스 게임 요청");

                BalanceResponseDto balanceResponseDto = null;
                balanceResponseDto = balanceService.findByBalanceSeq();

                String balance_q_request_tmp = String.format("{\n" +
                                "\t\"key\" : \"balance_q_response_1\",\n" +
                                "\t\"question\" :\"%s\",\n" +
                                "\t\"answer1\" : \"%s\",\n" +
                                "\t\"answer2\" : \"%s\"\n" +
                                "}", balanceResponseDto.getQuestion(),
                        balanceResponseDto.getAnswer1(),
                        balanceResponseDto.getAnswer2());
                TextMessage balance_q_request_msg = new TextMessage(balance_q_request_tmp);
                for (int roomcnt = 0; roomcnt < room1vs1.size(); roomcnt++) {
                    try {
                        if (session.equals(room1vs1.get(roomcnt).getUser1Session())| session.equals(room1vs1.get(roomcnt).getUser2Session())) {
                            log.info("d");
                            sendRoomMessage(roomcnt, balance_q_request_msg);
                        }
                    } catch (IndexOutOfBoundsException e) {
                        log.info("리스트를 벗어나 버렸습니다.");
                    } catch (IllegalStateException e) {
                        log.info("웹소켓이 이미 닫혔습니다");
                    }
                }
                break;
            case "balance_q_request_2":
                log.info("밸런스 게임 요청");
                BalanceResponseDto balanceResponseDto2 = balanceService.findByBalanceSeq();

                String balance_q_request_tmp2 = String.format("{\n" +
                                "\t\"key\" : \"balance_q_response_2\",\n" +
                                "\t\"question\" :\"%s\",\n" +
                                "\t\"answer1\" : \"%s\",\n" +
                                "\t\"answer2\" : \"%s\"\n" +
                                "}", balanceResponseDto2.getQuestion(),
                        balanceResponseDto2.getAnswer1(),
                        balanceResponseDto2.getAnswer2());
                TextMessage balance_q_request_msg2 = new TextMessage(balance_q_request_tmp2);
                for (int roomcnt = 0; roomcnt < room2vs2.size(); roomcnt++) {
                    try {
                        sendUserMessage2Wooyeong(roomcnt, session, balance_q_request_msg2);
                    } catch (IndexOutOfBoundsException e) {
                        log.info("리스트를 벗어나 버렸습니다.");
                    } catch (IllegalStateException e) {
                        log.info("웹소켓이 이미 닫혔습니다");
                    }
                }
                break;

            case "balance_a_request_1":
                log.info("밸런스 게임 답 요청");
                String balance_a_request_tmp = String.format("{\n" +
                                "\t\"key\" : \"balance_a_response_1\",\n" +
                                "\t\"nickName\" : \"%s\",\n" +
                                "\t\"answer\" : \"%s\",\n" +
                                "\t\"question\" : \"%s\"\n" +
                                "}", jObject.get("nickName").toString()
                        , jObject.get("question").toString()
                        , jObject.get("answser").toString());
                TextMessage balance_a_request_msg = new TextMessage(balance_a_request_tmp);

                for (int roomcnt = 0; roomcnt < room1vs1.size(); roomcnt++) {
                    try {
                        sendRoomMessage_new(roomcnt, session, balance_a_request_msg);
                    } catch (IndexOutOfBoundsException e) {
                        log.info("리스트를 벗어나 버렸습니다.");
                    } catch (IllegalStateException e) {
                        log.info("웹소켓이 이미 닫혔습니다");
                    }
                }

                break;
            case "balance_a_request_2":
                log.info("밸런스 게임 답 요청");
                String balance_a_request_tmp2 = String.format("{\n" +
                                "\t\"key\" : \"balance_a_response_2\",\n" +
                                "\t\"answer\" : \"%s\",\n" +
                                "\t\"answer\" : \"%s\",\n" +
                                "\t\"question\" : \"%s\"\n" +
                                "}", jObject.get("nickName").toString()
                        , jObject.get("question").toString()
                        , jObject.get("answser").toString());
                TextMessage balance_a_request_msg2 = new TextMessage(balance_a_request_tmp2);

                for (int roomcnt = 0; roomcnt < room2vs2.size(); roomcnt++) {
                    try {
                        sendUserMessage2Wooyeong(roomcnt, session, balance_a_request_msg2);
                        break;
                    } catch (IndexOutOfBoundsException e) {
                        log.info("리스트를 벗어나 버렸습니다.");
                    } catch (IllegalStateException e) {
                        log.info("웹소켓이 이미 닫혔습니다");
                    }
                    break;
                }
                break;
            case "keyword_helper_request_1":
                log.info("키워드 헬퍼 요청");
                String keyword_helper_tmp = String.format("{\n" +
                        "\t\"key\" : \"balance_q_response_1\",\n" +
                        "\t\"keyword\" : \"%s\"\n" +
                        "}", jObject.get("keyword").toString());
                TextMessage keyword_helper_response_msg = new TextMessage(keyword_helper_tmp);
                for (int roomcnt = 0; roomcnt < room1vs1.size(); roomcnt++) {
                    try {

                        sendUserMessage(roomcnt, session, keyword_helper_response_msg);
                    } catch (IndexOutOfBoundsException e) {
                        log.info("리스트를 벗어나 버렸습니다.");
                    } catch (IllegalStateException e) {
                        log.info("웹소켓이 이미 닫혔습니다");
                    }

                }
                break;

            case "keyword_helper_request_2":
                log.info("키워드 헬퍼 요청");
                String keyword_helper_tmp2 = String.format("{\n" +
                        "\t\"key\" : \"balance_q_response_2\",\n" +
                        "\t\"keyword\" : \"%s\",\n" +
                        "}", jObject.get("keyword").toString());
                TextMessage keyword_helper_response_msg2 = new TextMessage(keyword_helper_tmp2);
                for (int roomcnt = 0; roomcnt < room2vs2.size(); roomcnt++) {
                    try {

                        sendUserMessage2(roomcnt, session, keyword_helper_response_msg2);
                        break;
                    } catch (IndexOutOfBoundsException e) {
                        log.info("리스트를 벗어나 버렸습니다.");
                    } catch (IllegalStateException e) {
                        log.info("웹소켓이 이미 닫혔습니다");
                    }

                }
                break;
            case "users_seq_request_1":
                log.info("방안의 유저 정보 요청 - 현재 사용하지 않습니다");


                for (int roomcnt = 0; roomcnt < room1vs1.size(); roomcnt++) {
                    try {
                        if (session.equals(room1vs1.get(roomcnt).getUser1Session()) | session.equals(room1vs1.get(roomcnt).getUser2Session())) {
                            String users_seq_response_tmp = String.format("{\n" +
                                    "\t\"key\" : \"users_seq_response_1\",\n" +
                                    "\t\"user1_seq\" : \"%s\",\n" +
                                    "\t\"user2_seq\" : \"%s\",\n" +
                                    "}", room1vs1.get(roomcnt).getUser1Seq(), room1vs1.get(roomcnt).getUser2Seq());
                            TextMessage users_seq_response_msg = new TextMessage(users_seq_response_tmp);
                            session.sendMessage(users_seq_response_msg);
                            break;
                        }

                    } catch (IndexOutOfBoundsException e) {
                        log.info("리스트를 벗어나 버렸습니다.");
                    } catch (IllegalStateException e) {
                        log.info("웹소켓이 이미 닫혔습니다");
                    }

                }
                break;

            case "users_seq_request_2":
                log.info("방안의 유저 정보 요청 - 현재 사용하지 않습니다");

                log.info("주의사항 : 무조건 4명 다 들어와있는 처음에 요청해야 합니다");
                for (int roomcnt = 0; roomcnt < room2vs2.size(); roomcnt++) {
                    try {

                        if (session.equals(room2vs2.get(roomcnt).getUserInfos().get(0).getSession()) |
                                session.equals(room2vs2.get(roomcnt).getUserInfos().get(1).getSession()) |
                                session.equals(room2vs2.get(roomcnt).getUserInfos().get(2).getSession()) |
                                session.equals(room2vs2.get(roomcnt).getUserInfos().get(3).getSession())) {
                            String users_seq_response_tmp = String.format("{\n" +
                                            "\t\"key\" : \"users_seq_response_2\",\n" +
                                            "\t\"user1_seq\" : \"%s\",\n" +
                                            "\t\"user2_seq\" : \"%s\",\n" +
                                            "\t\"user3_seq\" : \"%s\",\n" +
                                            "\t\"user4_seq\" : \"%s\",\n" +

                                            "}", room2vs2.get(roomcnt).getUserInfos().get(0).getSeq()
                                    , room2vs2.get(roomcnt).getUserInfos().get(1).getSeq()
                                    , room2vs2.get(roomcnt).getUserInfos().get(2).getSeq()
                                    , room2vs2.get(roomcnt).getUserInfos().get(3).getSeq());
                            TextMessage users_seq_response_msg = new TextMessage(users_seq_response_tmp);
                            session.sendMessage(users_seq_response_msg);
                            break;

                        }
                    } catch (IndexOutOfBoundsException e) {
                        log.info("리스트를 벗어나 버렸습니다.");
                    } catch (IllegalStateException e) {
                        log.info("웹소켓이 이미 닫혔습니다");
                    }
                }
                break;
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        list.add(session);

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
                    log.info("방이 사라졌습니다" + room1vs1.toString());
                }
            } catch (NullPointerException e) {
                log.info("한사람한테만 보냈습니다");
            }
        }

        for (int roomcnt = 0; roomcnt < room2vs2.size(); roomcnt++) {
            try {
                for (int userCnt = 0; userCnt < (room2vs2.get(roomcnt).getUserInfos().size()); userCnt++) {
                    try {
                        if (session.equals(room2vs2.get(roomcnt).getUserInfos().get(userCnt).getSession())) {

                            log.info(room2vs2.get(roomcnt).getUserInfos().get(userCnt));
                            room2vs2.get(roomcnt).getUserInfos().remove(userCnt);
                            log.info("유저가 나갔습니다 " + room1vs1.toString());
                            if (room2vs2.get(roomcnt).getUserInfos().size() == 0) {
                                room2vs2.remove(roomcnt);
                                log.info("방이 사라졌습니다 " + room1vs1.toString());
                            }
                        }
                    } catch (IllegalStateException i) {
                        log.info("이미 나간 사람에게는 메세지가 가지 않습니다");
                    }
                }
            } catch (NullPointerException e) {
                log.info("한사람한테만 보냈습니다");
            } catch (IndexOutOfBoundsException e) {
                log.info("이미 나간 사람한테는 메세지가 가지 않습니다.");
            }
        }
        log.info(session + " 클라이언트 접속 해제");

    }

    public void sendLike(int roomcnt, WebSocketSession session, TextMessage msg) throws IOException {
        if (session.equals(room1vs1.get(roomcnt).getUser1Session())) {
            room1vs1.get(roomcnt).setUser1Like(true);
            if (room1vs1.get(roomcnt).isUser2Like() == true) {
                room1vs1.get(roomcnt).getUser1Session().sendMessage(msg);
                room1vs1.get(roomcnt).getUser2Session().sendMessage(msg);
                return;
            }
        } else {
            room1vs1.get(roomcnt).setUser2Like(true);
            if (room1vs1.get(roomcnt).isUser1Like() == true) {
                room1vs1.get(roomcnt).getUser1Session().sendMessage(msg);
                room1vs1.get(roomcnt).getUser2Session().sendMessage(msg);
                return;
            }
        }
    }

    public void sendLike2(String roomNum, WebSocketSession session, TextMessage msg) throws IOException {
try{


        for (int roomcnt = 0; roomcnt < room2vs2.size(); roomcnt++) {

            try{
                if (roomNum.equals(room2vs2.get(roomcnt).getRoomNum())) {
                for (int userCnt = 0; userCnt < PEOPLE_NUM_2VS2; userCnt++) {

                    if (session.equals(room2vs2.get(roomcnt).getUserInfos().get(userCnt).getSession())) {
                        room2vs2.get(roomcnt).getUserInfos().get(userCnt).setLike(true);

                        int likeCnt = 0;

                        try {
                            for (int i = 0; i < PEOPLE_NUM_2VS2; i++) {
                                if (room2vs2.get(roomcnt).getUserInfos().get(i).isLike()) {
                                    log.info("하트가 눌렸습니다");
                                    likeCnt++;
                                }
                            }
                        } catch (Exception e) {
                            log.info("아직 모든 인원이 차지 않았습니다");
                        }
                        log.info(likeCnt);
                        if (likeCnt == PEOPLE_NUM_2VS2) {
                            for (int i = 0; i < PEOPLE_NUM_2VS2; i++) {
                                room2vs2.get(roomcnt).getUserInfos().get(i).getSession().sendMessage(msg);
                            }
                        }
                    }
                }
            }}catch(Exception e){
                log.info("이미 사라진 방은 체크되지 않습니다");
             }
        }}catch (Exception e){
    log.info("이미 사라진 방은 체크되지 않습니다");
        }
    }


    public void sendRoomMessage(int roomcnt, TextMessage msg) throws IOException, IllegalStateException {
        log.info("sendRoomMessage");
        room1vs1.get(roomcnt).getUser1Session().sendMessage(msg);
        room1vs1.get(roomcnt).getUser2Session().sendMessage(msg);
    }

    public void sendRoomMessage_new(int roomcnt, WebSocketSession session, TextMessage msg) throws IOException, IllegalStateException {
        log.info("새로운 sendRoommessage에 들어왔습니다");
        log.info(room1vs1.get(roomcnt).getUser1Session());
        log.info(room1vs1.get(roomcnt).getUser2Session());
        log.info(session);
        if (session.equals(room1vs1.get(roomcnt).getUser1Session()) | session.equals(room1vs1.get(roomcnt).getUser2Session())) {
                log.info("같습니다");
                room1vs1.get(roomcnt).getUser1Session().sendMessage(msg);
                room1vs1.get(roomcnt).getUser2Session().sendMessage(msg);

        }
    }


    public void sendUserMessage(int roomcnt, WebSocketSession session, TextMessage msg) throws IOException, IllegalStateException {
        if (session.equals(room1vs1.get(roomcnt).getUser1Session())) { //자기가1이면
            room1vs1.get(roomcnt).getUser2Session().sendMessage(msg); //2한테 메세지 보내기
        } else {//자기가 2라면
            room1vs1.get(roomcnt).getUser1Session().sendMessage(msg); //1한테 메세지 보내기
        }
    }


    public void sendUserMessage2(int roomcnt, WebSocketSession session, TextMessage msg) throws IOException, IllegalStateException {
        for (int i = 0; i < PEOPLE_NUM_2VS2; i++) {
            if (session.equals(room2vs2.get(roomcnt).getUserInfos().get(i).getSession())) continue;
            room2vs2.get(roomcnt).getUserInfos().get(i).getSession().sendMessage(msg);
        }
    }

    public void sendUserMessage2Wooyeong(int roomcnt, WebSocketSession session, TextMessage msg) throws IOException, IllegalStateException {
        for (int i = 0; i < PEOPLE_NUM_2VS2; i++) {
            room2vs2.get(roomcnt).getUserInfos().get(i).getSession().sendMessage(msg);
        }
    }
}
