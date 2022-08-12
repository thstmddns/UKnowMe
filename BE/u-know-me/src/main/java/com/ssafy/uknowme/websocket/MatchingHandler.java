package com.ssafy.uknowme.websocket;

import com.ssafy.uknowme.model.dto.ParticipationDto.ParticipationSaveRequestDto;

import com.ssafy.uknowme.model.dto.RoomDto.RoomSaveRequestDto;
import com.ssafy.uknowme.web.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
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
@Controller
@RequiredArgsConstructor
public class MatchingHandler extends TextWebSocketHandler {


    static List<User1vs1> connectUserList1vs1 = new ArrayList<>();  // waitingUserList 에서 넘어온 유저

    static List<User1vs1> ManList1vs1 = new ArrayList<>(); // watingUser 에서 넘어온 남자 리스트

    static List<User1vs1> WomanList1vs1 = new ArrayList<>(); //watingUser에서 넘어온 여자 리스트

    static double nowlat1vs1; // 현재 남자의 lat (위도)

    static double nowlon1vs1; // 현재 남자의 lon (경도)


    ///////////////


    static List<User2vs2> connectUserList2vs2 = new ArrayList<>();  //오픈비두 서버에서 정보가 전달된 유저

    static List<User2vs2> ManList2vs2; // watingUser 에서 넘어온 남자 리스트

    static List<User2vs2> WomanList2vs2; //watingUser에서 넘어온 여자 리스트

    static final int MATCHING_NUM = 2;


    private final ParticipationService participationService;
    private final RoomService roomService;


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        JSONObject jObject = new JSONObject(message.getPayload());
        String key = jObject.get("key").toString();

        switch (key) {
            case "match_start_1":
                log.info("1대1 대기방에 들어왔습니다");
                User1vs1 user1vs1 = new User1vs1();
                user1vs1.convertToJSOvject(jObject, session);
                connectUserList1vs1.add(user1vs1);

                addGenderList(connectUserList1vs1);
                startMatching1vs1();
                break;

            case "match_start_2":
                log.info("2대2 대기방에 들어왔습니다");
                User2vs2 user2vs2 = new User2vs2();
                user2vs2.convertToJSOvject(jObject, session);
                connectUserList2vs2.add(user2vs2);

                startMatching2vs2();
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info(session + "클라이언트 접속");
        session.sendMessage(new TextMessage("유노 : 웹소켓 서버에 온걸 환영해"));
    }

    /* Client가 접속 해제 시 호출되는 메서드드 */

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        log.info(session + " 클라이언트 접속 해제");
        session.sendMessage(new TextMessage("유노 : 다음에 또 와"));

        try {
            for (int cnt = 0; cnt < connectUserList1vs1.size(); cnt++) {
                if (connectUserList1vs1.get(cnt).getSession() == session) {
                    connectUserList1vs1.remove(cnt);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            log.info("몇명은 이미 연결이 끊어졌습니다.");
        }catch (NullPointerException e) {
            log.info("몇명은 이미 연결이 끊어졌습니다.");
        }


        try {
            for (int cnt = 0; cnt < ManList1vs1.size(); cnt++) {
                if (ManList1vs1.get(cnt).getSession() == session) {
                    ManList1vs1.remove(cnt);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            log.info("몇명은 이미 연결이 끊어졌습니다.");
        }catch (NullPointerException e) {
            log.info("몇명은 이미 연결이 끊어졌습니다.");
        }

        try {
            for (int cnt = 0; cnt < WomanList1vs1.size(); cnt++) {
                if (WomanList1vs1.get(cnt).getSession() == session) {
                    WomanList1vs1.remove(cnt);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            log.info("몇명은 이미 연결이 끊어졌습니다.");
        }catch (NullPointerException e) {
            log.info("몇명은 이미 연결이 끊어졌습니다.");
        }


        try {
            for (int cnt = 0; cnt < connectUserList2vs2.size(); cnt++) {
                if (connectUserList2vs2.get(cnt).getSession() == session) {
                    connectUserList2vs2.remove(cnt);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            log.info("몇명은 이미 연결이 끊어졌습니다.");
        }catch (NullPointerException e) {
            log.info("몇명은 이미 연결이 끊어졌습니다.");
        }


        try {
            for (int cnt = 0; cnt < ManList2vs2.size(); cnt++) {
                if (ManList1vs1.get(cnt).getSession() == session) {
                    ManList1vs1.remove(cnt);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            log.info("몇명은 이미 연결이 끊어졌습니다.");
        }catch (NullPointerException e) {
            log.info("몇명은 이미 연결이 끊어졌습니다.");
        }

        try {

            for (int cnt = 0; cnt < WomanList2vs2.size(); cnt++) {
                if (WomanList1vs1.get(cnt).getSession() == session) {
                    WomanList1vs1.remove(cnt);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            log.info("몇명은 이미 연결이 끊어졌습니다.");
        }catch (NullPointerException e) {
            log.info("몇명은 이미 연결이 끊어졌습니다.");
        }

    }




    //유저를 남자 리스트, 여자 리스트에 넣는 메소드
    public void addGenderList(List<User1vs1> watingUser1vs1) {
        //남자(M)이면 ManList에, 여자(W)이면 WomanlList에 넣는다.
        watingUser1vs1.forEach((user1vs1) -> {
            if (user1vs1.getGender() == 'M') {
                ManList1vs1.add(user1vs1);

            } else {
                WomanList1vs1.add(user1vs1);
            }
        });
        connectUserList1vs1.clear();
    }

    //매칭 시작하는 메소드
    public void startMatching1vs1() throws InterruptedException {
        for (int mancnt = 0; mancnt <= ManList1vs1.size(); mancnt++) {
            List<User1vs1> user1vs1TmpWomanList = new ArrayList<>(); //남자 기준의 임시 여자 리스트 생성
            try {
                for (int womancnt = 0; womancnt <= WomanList1vs1.size(); womancnt++) {
                    try {
                        // 현재 남자의 위도를 전역변수에 저장
                        nowlat1vs1 = ManList1vs1.get(mancnt).getLat();
                        // 현재 남자의 경도를 전역변수에 저장
                        nowlon1vs1 = ManList1vs1.get(mancnt).getLon();
                        //isMaching 메소드로 조건 확인
                        if (isMaching1vs1(WomanList1vs1.get(womancnt), ManList1vs1.get(mancnt)) & isMaching1vs1(ManList1vs1.get(mancnt), WomanList1vs1.get(womancnt))) {
                            user1vs1TmpWomanList.add(WomanList1vs1.get(womancnt));
                        }
                    } catch (IndexOutOfBoundsException e) {
                        log.info("여자리스트가 끝났습니다");
                        break;
                    }
                }
                //남자 기준으로 여자들을 거리순 정렬
                Collections.sort(user1vs1TmpWomanList, optionComparator);

                //임시 여자 리스트에서 남자와 매칭 시도
                for (User1vs1 womanUser1vs1 : user1vs1TmpWomanList) {
                    if (startConnect1vs1(ManList1vs1.get(mancnt), womanUser1vs1)) {

                        mancnt--;
                        break;
                    }
                    ;


                }
            } catch (IndexOutOfBoundsException e) {

                log.info("남자리스트가 끝났습니다");
                break;
            } catch (IOException e) {
                log.info("입출력 Exception 발생");
            }
        }
    }


    //2대2매칭 메소드
    public void startMatching2vs2() {
        for (int usercnt = 0; usercnt < connectUserList2vs2.size(); usercnt++) { //모든 유저에 대해 검색시작
            try {
                ManList2vs2 = new ArrayList<>(); //남자리스트 생성
                WomanList2vs2 = new ArrayList<>(); // 여자리스트 생성

                for (int musercnt = 0; musercnt < connectUserList2vs2.size(); musercnt++) { // 4명 탐색 시작

                    // 이미 리스트가 다 다 찬 경우
                    if (ManList2vs2.size() >= MATCHING_NUM & connectUserList2vs2.get(musercnt).getGender() == 'M')
                        continue;
                    if (WomanList2vs2.size() >= MATCHING_NUM & connectUserList2vs2.get(musercnt).getGender() == 'W')
                        continue;

                    //옵션 체크 위한 변수
                    boolean ckCondition = true;

                    //여자 리스트에서 옵션 체크
                    for (User2vs2 user1 : WomanList2vs2) {
                        if (!isMatching2v2(user1, connectUserList2vs2.get(musercnt)) | !isMatching2v2(connectUserList2vs2.get(musercnt), user1))
                            ckCondition = false;
                    }
                    //남자 리스트에서 옵션 체크
                    for (User2vs2 user1 : ManList2vs2) {
                        if (!isMatching2v2(user1, connectUserList2vs2.get(musercnt)) | !isMatching2v2(connectUserList2vs2.get(musercnt), user1))
                            ckCondition = false;
                    }
                    //맞는 조건이라면 남자, 여자 리스트 중 알맞은 리스트에 추가
                    if (ckCondition) {
                        if (connectUserList2vs2.get(musercnt).getGender() == 'M')
                            ManList2vs2.add(connectUserList2vs2.get(musercnt));
                        else WomanList2vs2.add(connectUserList2vs2.get(musercnt));

                    }
                }
                //4명이 다 찼다면 연결 시작
                if (WomanList2vs2.size() == MATCHING_NUM & ManList2vs2.size() == MATCHING_NUM) {
                    log.info("연결을 시작합니다");
                    if (startConnect2vs2(ManList2vs2.get(0), ManList2vs2.get(1), WomanList2vs2.get(0), WomanList2vs2.get(1))) {
                        usercnt -= 4;
                        break;
                    }
                }
            } catch (IndexOutOfBoundsException | IOException e) {
                log.info("리스트가 끝났습니다");
                break;
            }
        }
    }


    //1번 유저 기준으로 옵션 체크
    public boolean isMaching1vs1(User1vs1 user1, User1vs1 user2) {
        // 나이 체크
        if (user1.getMaxAge() < user2.getAge()) return false;
        if (user1.getMinAge() > user2.getAge()) return false;

        //옵션 조건 체크
        for (int i = 0; i < user1.getOptions().length; i++) {
            if (user1.getMatchingOptions()[i] == 2 & user2.getOptions()[i] == 1) return false;
            if (user1.getMatchingOptions()[i] == 1 & user2.getOptions()[i] == 2) return false;
        }
        return true;
    }



    //1번 유저 기준으로 옵션 체크 - 후에 옵션 추가시 이 메소드에 넣으면 됨
    public boolean isMatching2v2(User2vs2 user1, User2vs2 user2) {
        // 나이 체크
        if (user1.getMaxAge() < user2.getAge()) return false;
        if (user1.getMinAge() > user2.getAge()) return false;
        return true;
    }

    //매칭성공한 사람들을 현재 리스트에서 제거, 방번호 생성, 접속시잓
    public boolean startConnect2vs2(User2vs2 user1, User2vs2 user2, User2vs2 user3, User2vs2 user4) throws IOException {


        //TODO : TEST CODE 추후 삭제필
        log.info("연결된 유저  : " + user1.getNickname() + " " + user2.getNickname() + " " + user3.getNickname() + " " + user4.getNickname());

        String roomSeq = UUID.randomUUID().toString();
        TextMessage tx = new TextMessage(roomSeq);


        RoomSaveRequestDto roomSaveRequestDto = new RoomSaveRequestDto();
        roomSaveRequestDto.setSeq(roomSeq);
        roomSaveRequestDto.setType("match_start_2");

        roomService.save(roomSaveRequestDto);


        ParticipationSaveRequestDto user1ParticipationSaveRequestDto = new ParticipationSaveRequestDto();
        ParticipationSaveRequestDto user2ParticipationSaveRequestDto = new ParticipationSaveRequestDto();
        ParticipationSaveRequestDto user3ParticipationSaveRequestDto = new ParticipationSaveRequestDto();
        ParticipationSaveRequestDto user4ParticipationSaveRequestDto = new ParticipationSaveRequestDto();

        user1ParticipationSaveRequestDto.setRoomSeq(roomSeq);
        user1ParticipationSaveRequestDto.setMemberSeq(user1.getSeq());

        user2ParticipationSaveRequestDto.setRoomSeq(roomSeq);
        user2ParticipationSaveRequestDto.setMemberSeq(user2.getSeq());

        user3ParticipationSaveRequestDto.setRoomSeq(roomSeq);
        user3ParticipationSaveRequestDto.setMemberSeq(user3.getSeq());

        user4ParticipationSaveRequestDto.setRoomSeq(roomSeq);
        user4ParticipationSaveRequestDto.setMemberSeq(user4.getSeq());

        if (participationService.save(user1ParticipationSaveRequestDto)) {
            log.info("2인 유저 1 : DB 접근에 실패했습니다");
            return false;
        }


        if (participationService.save(user2ParticipationSaveRequestDto)) {
            log.info("2인 유저 2 : DB 접근에 실패했습니다");
            return false;
        }


        if (participationService.save(user3ParticipationSaveRequestDto)) {
            log.info("2인 유저 3 : DB 접근에 실패했습니다");
            return false;
        }

        if (participationService.save(user4ParticipationSaveRequestDto)) {
            log.info("2인 유저 4 : DB 접근에 실패했습니다");
            return false;
        }


        user1.getSession().sendMessage(tx);
        user2.getSession().sendMessage(tx);
        user3.getSession().sendMessage(tx);
        user4.getSession().sendMessage(tx);

        //현재 list 에서 유저 빼기
        connectUserList2vs2.remove(user1);
        connectUserList2vs2.remove(user2);
        connectUserList2vs2.remove(user3);
        connectUserList2vs2.remove(user4);

        //현재 남자, 여자 리스트 초기화
        ManList2vs2.clear();
        WomanList2vs2.clear();

        return true;

    }

    //매칭성공한 사람들을 현재 리스트에서 제거
    public boolean startConnect1vs1(User1vs1 user1, User1vs1 user2) throws IOException {

        String roomSeq = UUID.randomUUID().toString();
        TextMessage tx = new TextMessage(roomSeq);

        RoomSaveRequestDto roomSaveRequestDto = new RoomSaveRequestDto();
        roomSaveRequestDto.setSeq(roomSeq);
        roomSaveRequestDto.setType("match_start_1");

        roomService.save(roomSaveRequestDto);




        ParticipationSaveRequestDto user1ParticipationSaveRequestDto = new ParticipationSaveRequestDto();
        ParticipationSaveRequestDto user2ParticipationSaveRequestDto = new ParticipationSaveRequestDto();
        user1ParticipationSaveRequestDto.setRoomSeq(roomSeq);
        user1ParticipationSaveRequestDto.setMemberSeq(user1.getSeq());
        user2ParticipationSaveRequestDto.setRoomSeq(roomSeq);
        user2ParticipationSaveRequestDto.setMemberSeq(user2.getSeq());

        if (participationService.save(user1ParticipationSaveRequestDto)) {
            log.info("1인 매칭 남자유저 : DB 접근에 실패했습니다");
            return false;
        }


        if (participationService.save(user2ParticipationSaveRequestDto)) {
            log.info("1인 매칭 여자유저 : DB 접근에 실패했습니다.");
            return false;
        }

        user1.getSession().sendMessage(tx);
        user2.getSession().sendMessage(tx);

        //현재 남자, 여자 list 에서 빼기
        ManList1vs1.remove(user1);
        WomanList1vs1.remove(user2);

        return true;
    }

    //첫번째 남자의 위치로 정렬하는 comparator
    public Comparator<User1vs1> optionComparator = new Comparator<User1vs1>() {
        @Override
        public int compare(User1vs1 user1vs11, User1vs1 user1vs12) {
            //user1과 현재 남자의 두 점 사이 거리 측정
            double ayd1 = Math.pow((user1vs11.getLat() - nowlat1vs1), 2);
            double axd1 = Math.pow((user1vs11.getLon() - nowlon1vs1), 2);
            double user1far = Math.sqrt(ayd1 + axd1);

            //user2와 현재 남자의 두 점 사이 거리 측정
            double ayd2 = Math.pow((user1vs12.getLat() - nowlat1vs1), 2);
            double axd2 = Math.pow((user1vs12.getLon() - nowlon1vs1), 2);
            double user2far = Math.sqrt(ayd2 + axd2);

            if (user1far > user2far) return 1;
            else return -1;
        }
    };
}
