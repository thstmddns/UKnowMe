package com.ssafy.uknowme.sockjs;

import com.ssafy.uknowme.model.dto.MatchingDto.MatchingRequestDto;
import com.ssafy.uknowme.model.dto.MatchingDto.MatchingResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.map.MultiValueMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.*;


@CrossOrigin
@Component
@Log4j2
public class ChatHandler extends TextWebSocketHandler {


    static List<User> connectUser = new ArrayList<>();  //오픈비두 서버에서 정보가 전달된 유저

    static List<User> ManList = new ArrayList<>(); // watingUser 에서 넘어온 남자 리스트

    static List<User> WomanList = new ArrayList<>(); //watingUser에서 넘어온 여자 리스트

    static double nowlat; // 현재 남자의 lat (위도)

    static double nowlon; // 현재 남자의 lon (경도)
    private static List<WebSocketSession> list = new ArrayList<>();

    /* Client가 접속 시 호출되는 메서드 */

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload : " + payload);
        JSONObject jObject = new JSONObject(message);


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
                new int[Integer.parseInt(jObject.getString("machingSmoke"))]


        ));

        int[] smokeo = {0}; // 상관없음
        int[] smokex = {1}; // 담배핌
        connectUser.add(new User(1, "yesol1", "예솔1", 'W', 25, 23, 24, 0.2, 0.2, smokeo, smokeo));
        connectUser.add(new User(1, "yesol1", "예솔1", 'M', 25, 23, 24, 0.2, 0.2, smokeo, smokeo));
        connectUser.get(0).setNickname(String.valueOf(message));
        addGenderList(connectUser);
        startMatching1vs1();
        deleteoutUser();


        for(WebSocketSession sess: list) {
            sess.sendMessage(message);
        }
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
    }


    public static void deleteoutUser() {
        for (int mancnt = 0; mancnt <= ManList.size(); mancnt++) {
            //TODO : [openvidu] 서버와 통신해서 소켓에 사람 남아 있는지 확인할 것. 아래 코드를 참조하면 됩니다.
            //if ManList.get(mancnt).getSeq()가 여기 없으면
            // ManList.remove(ManList.get(mancnt));

        }
        for (int womancnt = 0; womancnt <= WomanList.size(); womancnt++) {
            //TODO : [openvidu] 서버와 통신해서 소켓에 사람 남아 있는지 확인할 것. 아래 코드를 참조하면 됩니다.
            //if WomanList.get(womancnt).getSeq()가 여기 없으면
            // ManList.remove(WomanList.get(womancnty));

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
    }

    //매칭 시작하는 메소드
    public static void startMatching1vs1() {
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
                        //TODO : logger 로 교체
                        System.out.println("여자리스트가 끝나버렸습니다.");
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
                //TODO : logger 로 교체
                System.out.println("남자리스트가 끝나버렸습니다.");
                break;
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
    public static boolean startConnect(User user1, User user2) {
        // TODO : [openvidu] 현재 웹소켓에 이사람들이 접속해 있는지 확인하깋

        //현재 남자, 여자 list 에서 빼기
        ManList.remove(user1);
        WomanList.remove(user2);

        //TODO : connectlist 에서 삭제하기

        //TODO : TEST CODE 추후 삭제필
        System.out.println(user1.getNickname() + " " + user2.getNickname());

        //방번호규칙 : 현재시간 + 유저ID
        Date today = new Date();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "yyyyMMddHHmmss";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern,
                currentLocale);
        //TODO : TEST CODE 추후 삭제필
        System.out.println("ROOM NUMBER : " + formatter.format(today) + user1.getId());
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
