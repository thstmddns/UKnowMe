package com.ssafy.uknowme.openvidu.controller;

import com.ssafy.uknowme.openvidu.dto.request.ConnectionRequestDto;
import com.ssafy.uknowme.openvidu.dto.response.ConnectionResponseDto;
import io.openvidu.java.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
public class SessionController {

    /**
     * 이 객체를 이용해 세션을 생성할 수 있습니다.
     */
    private final OpenVidu openVidu;

    /**
     * 생성된 세션들은 여기에서 관리됩니다.
     */
    private final Map<Integer, Session> sessions = new ConcurrentHashMap<>();

    /**
     * 세션에 연결된 연결들의 토큰 정보가 여기에서 관리됩니다.
     */
    private final Map<Integer, Map<String, OpenViduRole>> tokens = new ConcurrentHashMap<>();

    private final String OPENVIDU_URL;

    private final String SECRET;


    public SessionController(@Value("${openvidu.url}") String url, @Value("${openvidu.secret}") String secret) {
        this.OPENVIDU_URL = url;
        this.SECRET = secret;
        this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
    }

    @PostMapping("/session")
    public ResponseEntity<?> connect(@RequestBody ConnectionRequestDto dto) {

        int roomSeq = dto.getRoomSeq();

        // 커넥션 프로퍼티, 현재 연결될 사용자의 프로퍼티를 설정한다.
        ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
                .type(ConnectionType.WEBRTC)
                .role(OpenViduRole.PUBLISHER)
                .data(null)
                .build();

        Session session = getSession(roomSeq);

        sessions.put(roomSeq, session);

        // 사용자가 사용할 토큰을 생성한다.
        String token = getToken(connectionProperties, session);

        // tokens에서 현재 생성한 토큰을 서버에서 관리한다.
        if (!tokens.containsKey(roomSeq)) tokens.put(roomSeq, new ConcurrentHashMap<>());
        tokens.get(roomSeq).put(token, connectionProperties.getRole());

        //해당 세션의 방 정보와 멤버 정보를 API로 넘겨줄 예정
        return new ResponseEntity<>(new ConnectionResponseDto(token, null, null), HttpStatus.OK);
    }

    @DeleteMapping("/session")
    public ResponseEntity<?> disconnect(@RequestBody ConnectionRequestDto dto) {
        int roomSeq = dto.getRoomSeq();
        String token = dto.getToken();

        if (sessions.get(roomSeq) == null) {
            log.info("[Room {}] 해당 방의 세션은 존재하지 않습니다.", roomSeq);
            return new ResponseEntity<>("해당 방의 세션은 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        if (tokens.get(roomSeq) == null) {
            log.info("[Room {}] 해당 방의 토큰은 존재하지 않습니다.", roomSeq);
            return new ResponseEntity<>("해당 방의 토큰은 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        if (tokens.get(roomSeq).remove(token) == null) {
            log.info("[token : {}] 해당 토큰은 사용되지 않는 토큰입니다.", token);
            return new ResponseEntity<>("해당 토큰은 사용되지 않는 토큰입니다.", HttpStatus.BAD_REQUEST);
        }

        // 세션의 마지막 사용자가 연결을 종료하면 세션을 삭제한다.
        if (tokens.get(roomSeq).isEmpty()) {
            // openvidu 서버에서 세션을 종료한다.
            closeSession(sessions.get(roomSeq));

            // WAS에서도 해당 세션의 상태를 메모리에서 제거한다.
            log.info("[Room {} - {}] 세션 종료", roomSeq, sessions.remove(roomSeq).getSessionId());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 사용자를 식별할 수 있는 토큰을 획득하는 메서드이다.
     * @param connectionProperties
     * @param session
     * @return 연결에 사용되는 토큰
     */
    private String getToken(ConnectionProperties connectionProperties, Session session) {
        try {
            return session.createConnection(connectionProperties).getToken();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 연결될 세션을 획득하는 메서드이다.
     * @param seq 생성된 방의 식별자
     * @return 연결될 세션
     */
    private Session getSession(int seq) {
        // 해당 seq의 세션이 없을 경우 세션을 생성한다.
        if (sessions.get(seq) == null) {
            try {
                return openVidu.createSession();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        // 해당 seq의 세션이 있을 경우 그 세션을 사용한다.
        else {
            try {
                return sessions.get(seq);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void closeSession(Session session) {
        try {
            session.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
