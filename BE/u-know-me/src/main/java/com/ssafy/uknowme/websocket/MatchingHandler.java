package com.ssafy.uknowme.websocket;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.json.JSONObject;
import java.io.IOException;
import java.util.*;

@CrossOrigin
@Component
@Log4j2
public class MatchingHandler extends TextWebSocketHandler {

//TODO : equals와 == 체크..할것
    static List<User> connectUser = new ArrayList<>();  // waitingUserList 에서 넘어온 유저

    static List<User> waitingUserList = new ArrayList<>();  //오픈비두 서버에서 연결되어서 대기 중인 유저

    static List<User> ManList = new ArrayList<>(); // watingUser 에서 넘어온 남자 리스트

    static List<User> WomanList = new ArrayList<>(); //watingUser에서 넘어온 여자 리스트

    static double nowlat; // 현재 남자의 lat (위도)

    static double nowlon; // 현재 남자의 lon (경도)
    private static List<WebSocketSession> list = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        //log.info("payload : " + payload);
        String parseMessage = payload.toString();

        log.info("parseMessage : " + payload);
        JSONObject jObject = new JSONObject(payload);
        log.info(jObject.toString());
        log.info("payload : " + parseMessage);

        connectUser.add(new User(
                Integer.parseInt(jObject.getString("seq")),
                jObject.getString("id"),
                jObject.getString("nickname"),
                jObject.getString("gender").charAt(0),
                Integer.parseInt(jObject.getString("maxage")),
                Integer.parseInt( jObject.getString("minage")),
                Integer.parseInt(jObject.getString("age")),
                Double.parseDouble(jObject.getString("lat")),
                Double.parseDouble( jObject.getString("lon")),
                new int[Integer.parseInt(jObject.getString("smoke"))],
                new int[Integer.parseInt(jObject.getString("machingSmoke"))],
                session
        ));

        addGenderList(connectUser);
        startMatching1vs1();
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        list.add(session);

        System.out.println(list.toString());

        log.info(session + " 클라이언트 접속"+list.toString() );
    }

    /* Client가 접속 해제 시 호출되는 메서드드 */

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        log.info(session + " 클라이언트 접속 해제");

        list.remove(session);

      for (int cnt=  0 ; cnt<connectUser.size(); cnt++){
          if(connectUser.get(cnt).getSession() == session){
              connectUser.remove(cnt);
          }
      }


        for (int cnt=  0 ; cnt<ManList.size(); cnt++){
            if(ManList.get(cnt).getSession() == session){
                ManList.remove(cnt);
            }
        }

        for (int cnt=  0 ; cnt<WomanList.size(); cnt++){
            if(WomanList.get(cnt).getSession() == session){
                WomanList.remove(cnt);
            }
        }
    }


    //유저를 남자 리스트, 여자 리스트에 넣는 메소드
    public static void addGenderList(List<User> watingUser) {
        //남자(M)이면 ManList에, 여자(W)이면 WomanlList에 넣는다.
        watingUser.forEach((user) -> {
            if (user.getGender() == 'M') {
                ManList.add(user);

            } else {
                WomanList.add(user);
            }
        });
        connectUser.clear();
    }

    //매칭 시작하는 메소드
    public static void startMatching1vs1() throws InterruptedException {
        for (int mancnt = 0; mancnt <= ManList.size(); mancnt++) {
            List<User> UserTmpWomanList = new ArrayList<>(); //남자 기준의 임시 여자 리스트 생성
            try {
                for (int womancnt = 0; womancnt <= WomanList.size(); womancnt++) {
                    try {
                        // 현재 남자의 위도를 전역변수에 저장
                        nowlat = ManList.get(mancnt).getLat();
                        // 현재 남자의 경도를 전역변수에 저장
                        nowlon =  ManList.get(mancnt).getLon();
                        //isMaching 메소드로 조건 확인
                        if (isMaching(WomanList.get(womancnt), WomanList.get(womancnt)) & isMaching(WomanList.get(womancnt), WomanList.get(womancnt))) {
                            UserTmpWomanList.add(WomanList.get(womancnt));
                        }
                    } catch (IndexOutOfBoundsException e) {
                        log.info("여자리스트가 끝났습니다");
                        break;
                    }
                }
                //남자 기준으로 여자들을 거리순 정렬
                Collections.sort(UserTmpWomanList, optionComparator);

                //임시 여자 리스트에서 남자와 매칭 시도
                for (User womanUser : UserTmpWomanList) {
                    if (startConnect(ManList.get(mancnt), womanUser)) {
                        mancnt--;
                        break;
                    }
                }
            } catch (IndexOutOfBoundsException e) {

                log.info("남자리스트가 끝났습니다");
                break;
            }catch (IOException e){
                log.info("입출력 Exception 발생");
            }
        }
    }


    //1번 유저 기준으로 옵션 체크
    public static boolean isMaching(User user1, User user2) {
        // 나이 체크
        if (user1.getMaxage() < user2.getAge()) return false;
        if (user1.getMinage() > user2.getAge()) return false;

        //옵션 조건 체크
        for (int i = 0; i < user1.getOptions().length; i++) {
            if (user1.getMatchoptions()[i] == 2 & user2.getOptions()[i] == 1) return false;
            if (user1.getMatchoptions()[i] == 1 & user2.getOptions()[i] == 2) return false;
        }
        return true;
    }


    //매칭성공한 사람들을 현재 리스트에서 제거
    public static boolean startConnect(User user1, User user2) throws IOException {
        //현재 남자, 여자 list 에서 빼기
        ManList.remove(user1);
        WomanList.remove(user2);

        String roomSeq = UUID.randomUUID().toString();
        for(WebSocketSession sess: list) {
            if(user1.getSession() == sess | user2.getSession()==sess){
                TextMessage tx = new TextMessage(roomSeq);
                sess.sendMessage(tx);
            }
        }

        return true;

    }

    //첫번째 남자의 위치로 정렬하는 comparator
    public static Comparator<User> optionComparator = new Comparator<User>() {
        @Override
        public int compare(User user1, User user2) {
            //user1과 현재 남자의 두 점 사이 거리 측정
            double ayd1 = Math.pow((user1.getLat() - nowlat), 2);
            double axd1 = Math.pow((user1.getLon() - nowlon), 2);
            double user1far = Math.sqrt(ayd1 + axd1);

            //user2와 현재 남자의 두 점 사이 거리 측정
            double ayd2 = Math.pow((user2.getLat() - nowlat), 2);
            double axd2 = Math.pow((user2.getLon() - nowlon), 2);
            double user2far = Math.sqrt(ayd2 + axd2);

            if (user1far > user2far) return 1;
            else return -1;
        }
    };

}
