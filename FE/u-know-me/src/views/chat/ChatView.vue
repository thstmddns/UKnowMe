<template>
  <div class="chat-body" id="main-container">
    <div id="join" v-if="!session">
      <div id="join-dialog" class="jumbotron vertical-center">
        <h1>Join a video session</h1>
        <div class="form-group">
          <p>
            <label>Participant</label>
            <input
              v-model="myUserName"
              class="form-control"
              type="text"
              required
            />
          </p>
          <p>
            <label>Session</label>
            <input
              v-model="mySessionId"
              class="form-control"
              type="text"
              required
            />
          </p>
          <p class="text-center">
            <button
              class="btn btn-lg btn-success"
              id="joinBtn"
              @click="joinSession()"
            >
              Join!
            </button>
          </p>
        </div>
      </div>
    </div>
    <div id="session" v-if="session">
      <div
        :class="{
          'video-container1': main.option.matchingRoom == 1,
          'video-container2': main.option.matchingRoom == 2,
        }"
      >
        <div class="video-item" id="my-video">
          <video
            :class="{
              'my-real-video1': main.option.matchingRoom == 1,
              'my-real-video2': main.option.matchingRoom == 2,
            }"
            style="display: none"
          ></video>
          <video
            id="test-video"
            style="display: none"
            autoplay
            controls
          ></video>
          <div class="preview">
            <video
              class="input_video"
              style="position: absolute; left: 0%"
            ></video>
            <video
              class="input_video2"
              style="position: absolute; left: 0%; display: none"
            ></video>
            <canvas
              class="guides"
              style="position: absolute; left: 0%"
            ></canvas>
          </div>
          <div><p>My Video</p></div>
        </div>
        <user-video
          v-for="sub in subscribers"
          :key="sub.stream.connection.connectionId"
          :stream-manager="sub"
          @click="updateMainVideoStreamManager(sub)"
        />
      </div>
    </div>
    <chat-sub />
    <accuse-modal v-if="chat.accuseBtn === 1" />
    <game-modal v-if="chat.gameBtn === 1" />
    <!-- <chat-something /> -->
  </div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "@/components/chat/UserVideo";
import { useChatStore } from "@/stores/chat/chat";
import { storeToRefs } from "pinia";
import { onMounted } from "vue";
import { useAccountStore } from "@/stores/land/account";

// import ChatSomething from "@/components/chat/ChatSomething.vue";
import ChatSub from "@/components/chat/ChatSub.vue";
import AccuseModal from "@/components/chat/AccuseModal.vue";
import GameModal from "@/components/chat/GameModal.vue";
import { useMainStore } from "@/stores/main/main";

axios.defaults.headers.post["Content-Type"] = "application/json";

// const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
const OPENVIDU_SERVER_URL = "https://i7d104.p.ssafy.io:4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
  name: "App",

  components: {
    UserVideo,
    ChatSub,
    AccuseModal,
    GameModal,
  },
  setup() {
    const account = useAccountStore();
    const chat = useChatStore();
    const main = useMainStore();
    account.fetchCurrentUser();

    let {
      OV,
      session,
      mainStreamManager,
      publisher,
      subscribers,
      videoDevices,
      SessionName,
    } = storeToRefs(chat);
    chat.socketConnect(account.currentUser.seq);

    //1:1 2:2 UI 판별

    main;
    // if(main.option.matchingRoom == "1"){
    // }

    onMounted(() => {
      document.getElementById("joinBtn").click();
    });

    setInterval(() => {
      chat.getTime();
    }, 1000);

    return {
      main,
      account,
      chat,
      OV,
      session,
      mainStreamManager,
      publisher,
      subscribers,
      videoDevices,
      SessionName,
    };
  },

  data() {
    return {
      mySessionId: this.SessionName,
      myUserName: this.account.currentUser.nickname,
    };
  },
  methods: {
    joinSession() {
      const chat = useChatStore();
      const account = useAccountStore();

      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- Init Video Device ---
      this.OV.getDevices().then((devices) => {
        this.videoDevices = devices.filter(
          (device) => device.kind === "videoinput"
        );
      });

      // --- Init a session ---
      this.session = this.OV.initSession();

      // --- Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber);
      });

      // On every Stream destroyed...
      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken(this.mySessionId).then((token) => {
        this.session
          .connect(token, { clientData: this.myUserName })
          .then(async () => {
            await chat.avatarLoad(account.currentUser.avatar.seq);
            var avatarVideo = await chat.startHolistic();

            // --- Get your own camera stream with the desired properties ---
            let publisher = this.OV.initPublisher(undefined, {
              audioSource: false, // The source of audio. If undefined default microphone
              videoSource: avatarVideo, // The source of video. If undefined default webcam
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

            this.session.publish(this.publisher);
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });

      window.addEventListener("beforeunload", chat.leaveSession);
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },

    /**
     * --------------------------
     * SERVER-SIDE RESPONSIBILITY
     * --------------------------
     * These methods retrieve the mandatory user token from OpenVidu Server.
     * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
     * the API REST, openvidu-java-client or openvidu-node-client):
     *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
     *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
     *   3) The Connection.token must be consumed in Session.connect() method
     */

    getToken(mySessionId) {
      return this.createSession(mySessionId).then((sessionId) =>
        this.createToken(sessionId)
      );
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
    createSession(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId,
            }),
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.id))
          .catch((error) => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
              );
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                )
              ) {
                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-connection
    createToken(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            {},
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    },
  },
};
</script>

<style>
h1 {
  margin: 0;
}
#join,
#session {
  position: relative;
  flex: 1;
}
.chat-body {
  display: flex;
  flex-direction: column;
  height: 100vh;
  /* background: radial-gradient(
    61.17% 61.17% at 50% 50%,
    #ebdcfe 56.77%,
    #ffffff 100%
  ); */
}

.video-container1 {
  position: absolute;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  width: 100vw;
  height: calc(100vh - 200px);
  max-height: calc((100vw / 2 -40px) / 4 * 3 + 60px);
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.video-container2 {
  position: absolute;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  width: calc((100vh - 220px) / 3 * 5);
  height: calc(100vh - 200px);
  max-height: calc(100vw * 3 / 4);
  max-width: 100vw;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.video-item {
  position: relative;
  height: calc((100vh - 200px) / 2 - 40px);
  max-height: calc(100vw * 3 / 8);
  max-width: calc(100vw / 2 - 40px);
  margin: 20px;
  text-align: center;
}
/* 1:1일때 canvas 크기 */
#avatarCanvas1,
.my-real-video1 {
  border: 3px solid purple;
  border-radius: 20px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  width: auto !important;
  max-width: calc(100vw / 2 - 40px) !important;
  height: calc(100vh - 260px) !important;
  max-height: calc((100vw / 2 - 40px) * 3 / 4) !important;
}
/* 2:2일때 canvas 크기 */
#avatarCanvas2,
.my-real-video2 {
  border: 3px solid purple;
  border-radius: 20px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  width: auto !important;
  max-width: calc(100vw / 2 - 40px) !important;
  height: calc((100vh - 200px) / 2 - 80px) !important;
  max-height: calc((100vw / 2 - 40px) * 3 / 4) !important;
}
.my-real-video1,
.my-real-video2 {
  transform: rotateY(180deg);
  -webkit-transform: rotateY(180deg); /* Safari and Chrome */
  -moz-transform: rotateY(180deg); /* Firefox */
}
.preview {
  position: absolute;
  width: 30%;
  height: auto;
  left: 5%;
  top: 5%;
  transform: rotateY(180deg);
  -webkit-transform: rotateY(180deg); /* Safari and Chrome */
  -moz-transform: rotateY(180deg); /* Firefox */
}
.preview video {
  width: 100% !important;
  height: auto;
  border: 3px solid purple;
  border-radius: 20px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
.preview .guides {
  width: 100% !important;
  height: auto;
  border: 3px solid rgba(0, 0, 0, 0);
  border-radius: 20px;
}
.nickName {
  height: 20px;
}
</style>