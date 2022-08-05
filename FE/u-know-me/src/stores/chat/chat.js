import { defineStore } from 'pinia'

export const useChatStore = defineStore('chat', {
  state: () => ({
    test: 0,
    webSocket: null,
    OV: undefined,
    session: undefined,
    mainStreamManager: undefined,
    publisher: undefined,
    subscribers: [],
  }),
  getters: {

  },
  actions: {
    socketConnect() {
      //socket test
      console.log("socket test");
      // 1. 웹소켓 클라이언트 객체 생성
      const webSocket = new WebSocket("ws://uknowme.mooo.com:8080/ws/chat");

      this.webSocket = webSocket;

      // 2. 웹소켓 이벤트 처리
      // 2-1) 연결 이벤트 처리
      webSocket.onopen = () => {
        console.log("웹소켓서버와 연결 성공");
        webSocket.send(`{
          "key" : "chat_start_1",
          "room_seq" : "cavavsdv-sadvas-asdvas2"
        }`);
      };

      // 2-2) 메세지 수신 이벤트 처리
      webSocket.onmessage = function (event) {
        console.log(`서버 웹소켓에게 받은 데이터: ${event.data}`);
      };

      // 2-3) 연결 종료 이벤트 처리
      webSocket.onclose = function () {
        console.log("서버 웹소켓 연결 종료");
      };

      // 2-4) 에러 발생 이벤트 처리
      webSocket.onerror = function (event) {
        console.log(event);
      };
    },

    heartClick() {
      this.webSocket.send(`{
        "key" : "heart_1",
        "room" : "cavavsdv-sadvas-asdvas2"
      }`);
      
      // this.toCam();
    },

    toCam() {
      let publisher = this.OV.initPublisher(undefined, {
        audioSource: undefined, // The source of audio. If undefined default microphone
        videoSource: undefined, // The source of video. If undefined default webcam
        publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
        publishVideo: true, // Whether you want to start publishing with your video enabled or not
        resolution: "640x480", // The resolution of your video
        frameRate: 30, // The frame rate of your video
        insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
        mirror: true, // Whether to mirror your local video or not
      });

      this.mainStreamManager = publisher;
      this.publisher = publisher;

      // --- Publish your stream ---
    }
  },
})