<template>
  <div class="button-list">
    <button class="main-btn" @click="main.btnCh = 1">
      로그아웃&#160;&#160;&#160;<i
        class="fa-solid fa-arrow-right-from-bracket"
      ></i>
    </button>
    <button class="main-btn" @click="main.btnCh = 2">
      정보수정&#160;&#160;&#160;<i class="fa-solid fa-gear"></i>
    </button>
  </div>
  <!-- <div class="metaverse">
      <div class="metaverse-img">
        <img class="metaverse-img" src="@/assets/main/metaverse.png" alt="">
      </div>
    </div> -->

  <hgroup class="speech-bubble">
    <h2 id="speech-title">대기 중</h2>
    <p id="speech-text">매칭 옵션 선택 후<br />매칭을 시작해주세요!</p>
  </hgroup>

  <div class="match-circle">
    <div id="love-container">
      <!-- <div class="heart-img" @click="this.$router.push({ name: 'chat' })"> -->
      <div class="heart-img" @click="matchStart()">
        <img id="heart-img-src" src="@/assets/main/heart.png" alt="" />
      </div>
      <div class="circle" style="animation-delay: 0s"></div>
      <div class="circle" style="animation-delay: 1s"></div>
      <div class="circle" style="animation-delay: 2s"></div>
      <div class="circle" style="animation-delay: 3s"></div>
    </div>

    <button class="matching-btn" @click="main.btnCh = 4">매칭 옵션 선택</button>
  </div>
</template>

<script>
import { useMainStore } from "@/stores/main/main";
import { useAccountStore } from "@/stores/land/account";
import { useChatStore } from "@/stores/chat/chat";
import router from "@/router";
import { storeToRefs } from "pinia";

export default {
  name: "ButtonList",
  components: {},
  data() {
    return {
      matchBtn: false,
      webSocket: null,
    };
  },
  setup() {
    const main = useMainStore();
    const account = useAccountStore();
    const chat = useChatStore();

    let { SessionName } = storeToRefs(chat);

    return { main, account, SessionName };
  },
  methods: {
    matchStart() {
      if (this.matchBtn == false) {
        const self = this;
        this.matchBtn = true;

        console.log("socket start");
        // 1. 웹소켓 클라이언트 객체 생성
        self.webSocket = new WebSocket(
          "wss://uknowme.mooo.com:8443/ws/matching"
        );

        // 2. 웹소켓 이벤트 처리
        // 2-1) 연결 이벤트 처리
        this.webSocket.onopen = () => {
          console.log("서버 웹소켓 연결 성공");

          var heartBtn = document.querySelector(".heart-img");
          heartBtn.style.width = "100px";
          heartBtn.style.height = "100px";
          document.getElementById("heart-img-src").style.animationDuration =
            "1s";
          document.getElementById("speech-title").innerHTML = "매칭";
          document.getElementById("speech-text").innerHTML =
            "매칭중입니다.<br>잠시만 기다려주세요..";

          document.querySelector(".matching-btn").disabled = true;

          try {
            let age = this.calcAge(this.account.currentUser.birth);
            let smoke = this.account.currentUser.smoke;

            let sendData = `{
            "key" : "match_start_1",
            "id" : "${this.account.currentUser.id}",
            "seq" : "${this.account.currentUser.seq}",
            "gender" : "${this.account.currentUser.gender}",
            "nickName":"${this.account.currentUser.nickname}",
            "age" : "${age}",
            "maxAge":"${age + this.main.option.maxAge}",
            "minAge":"${age - this.main.option.maxAge}",
            "lat":"10.0",
            "lon":"10.0",
            "smoke" : "${smoke}",
            "matchingSmoke":"${this.main.option.matchingSmoke}"
          }`;
            console.log("webSocket.send : ", sendData);
            this.webSocket.send(sendData);
          } catch (error) {
            this.webSocket.close();
            document.getElementById("speech-title").innerHTML = "오류 발생";
            document.getElementById("speech-text").innerHTML =
              "사용자 데이터 오류.<br>로그인을 확인해 주세요.";
            document.getElementById("heart-img-src").style.animationDuration =
              "0s";
            document.querySelector(".matching-btn").disabled = false;
          }
        };

        // 2-2) 메세지 수신 이벤트 처리
        this.webSocket.onmessage = function (event) {
          console.log(`서버 웹소켓에게 받은 데이터: ${event.data}`);

          document.getElementById("heart-img-src").style.animationDuration =
            "0.5s";
          document.getElementById("speech-title").innerHTML = "매칭 완료";
          document.getElementById("speech-text").innerHTML =
            "매칭완료!<br>곧 연결됩니다!";

          self.SessionName = event.data;

          setTimeout(() => router.push({ name: "chat" }), 3000);
        };

        // 2-3) 연결 종료 이벤트 처리
        this.webSocket.onclose = function () {
          console.log("서버 웹소켓 연결 종료");

          var heartBtn = document.querySelector(".heart-img");
          heartBtn.style.width = "50px";
          heartBtn.style.height = "50px";
          document.getElementById("heart-img-src").style.transform =
            "translate(-50%, -50%) rotate(0deg)";
        };

        // 2-4) 에러 발생 이벤트 처리
        this.webSocket.onerror = function (event) {
          console.log("error", event);
        };
      } else {
        this.matchBtn = false;
        this.webSocket.close();
        document.getElementById("speech-title").innerHTML = "대기 중";
        document.getElementById("speech-text").innerHTML =
          "매칭이 취소되었습니다.<br>매칭을 시작해주세요.";
        document.getElementById("heart-img-src").style.animationDuration = "0s";
        document.querySelector(".matching-btn").disabled = false;
      }
    },
    calcAge(ssn1) {
      var today = new Date();
      var result = today.getFullYear() - parseInt(ssn1.substring(0, 4), 10);

      return result;
    },
  },
};
</script>

<style>
.button-list {
  position: absolute;
  right: 50px;
  top: 50px;
  width: min-content;
}

.main-btn {
  width: 170px;
  height: 65px;
  padding: 10px 25px;
  margin: 15px;
  border: 2px none;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 20px;
  background: white;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  display: inline-block;
  overflow: hidden;
  font-size: 20px;
  font-weight: 600;
}

.main-btn:hover {
  background: #c699ff;
  color: #fff;
}
.main-btn:before {
  position: absolute;
  display: inline-block;
  top: -180px;
  left: 0;
  width: 30px;
  height: 100%;
  background-color: #fff;
  animation: shiny-btn1 3s ease-in-out infinite;
}
.main-btn:active {
  box-shadow: 4px 4px 6px 0 rgba(255, 255, 255, 0.3),
    -4px -4px 6px 0 rgba(116, 125, 136, 0.2),
    inset -4px -4px 6px 0 rgba(255, 255, 255, 0.2),
    inset 4px 4px 6px 0 rgba(0, 0, 0, 0.2);
}

.metaverse {
  position: absolute;
  bottom: 43%;
  left: 50%;
  transform: translate(-50%, 0%);
  cursor: pointer;
}

.metaverse-img {
  width: 150px;
}

.metaverse-img :hover {
  filter: brightness(90%);
}

.match-circle {
  position: absolute;
  right: 50px;
  bottom: 50px;
  display: grid;
}
#love-container {
  width: 200px;
  height: 200px;
  display: grid;
  place-items: center;
  overflow: hidden;
  position: relative;
}
.circle {
  border-radius: 50%;
  width: 80px;
  height: 80px;
  background-color: #a056ff;
  position: absolute;
  opacity: 0;

  animation: scaleIn 4s infinite cubic-bezier(0.36, 0.11, 0.89, 0.32);
}

@keyframes scaleIn {
  from {
    transform: scale(0.5, 0.5);
    opacity: 1;
  }
  to {
    transform: scale(2.5, 2.5);
    opacity: 0;
  }
}

#heart-img-src {
  transition: 0.5s;
  animation-name: heartSpin;
  animation-duration: 0s;
  animation-iteration-count: infinite;
}
@keyframes heartSpin {
  0% {
    transform: translate(-50%, -50%) rotate(0deg);
  }
  100% {
    transform: translate(-50%, -50%) rotate(360deg);
  }
}
.heart-img {
  background-color: white;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  z-index: 1;
  padding: 5px;
  cursor: pointer;
  filter: drop-shadow(0px 1.92647px 1.92647px rgba(0, 0, 0, 0.25));
  transition: 0.5s;
}
.heart-img img {
  width: 50px;
  height: 50px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.heart-img:hover {
  filter: brightness(90%);
}

.matching-btn {
  background-color: #dcddfe;
  width: 200px;
  height: 50px;
  border: none;
  margin: 0;
  border-radius: 30px;
  filter: drop-shadow(0px 1.92647px 1.92647px rgba(0, 0, 0, 0.25));
  font-size: 15px;
}
/* .matching-btn:hover {
  background: #dcddfe;
  color: black;
  cursor: default;
}
.matching-btn:active {
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
} */
.speech-bubble {
  position: absolute;
  right: 50px;
  bottom: 300px;
  background: #9ea2ff;
  border-radius: 20px;
  width: 200px;
  margin: 1em 0;
  text-align: center;
  line-height: 150%;
  color: white;
  font-weight: bold;
  text-shadow: 0px 1.92647px 1.92647px rgba(0, 0, 0, 0.25);
  filter: drop-shadow(0px 1.92647px 1.92647px rgba(0, 0, 0, 0.25));
  animation-name: bubbleAni;
  animation-duration: 3s;
  animation-iteration-count: infinite;
}

.speech-bubble:after {
  content: "";
  position: absolute;
  bottom: 0;
  left: 50%;
  width: 0;
  height: 0;
  border: 24px solid transparent;
  border-top-color: #9ea2ff;
  border-bottom: 0;
  border-left: 0;
  margin-left: 20px;
  margin-bottom: -20px;
}

@keyframes bubbleAni {
  0% {
    transform: translate(0%, 2%);
  }
  50% {
    transform: translate(0%, -2%);
  }
  100% {
    transform: translate(0%, 2%);
  }
}
</style>
