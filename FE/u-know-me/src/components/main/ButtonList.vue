<template>
  <div class="button-list">
    <button class="main-btn" @click="main.logoutBtn = true">
      로그아웃&#160;&#160;&#160;<i
        class="fa-solid fa-arrow-right-from-bracket"
      ></i>
    </button>
    <button class="main-btn" @click="main.informBtn = true">
      정보수정&#160;&#160;&#160;<i class="fa-solid fa-gear"></i>
    </button>
  </div>

  <!-- <div class="metaverse">
      <div class="metaverse-img">
        <img class="metaverse-img" src="@/assets/main/metaverse.png" alt="">
      </div>
    </div> -->

  <div class="match-circle">
    <div id="container">
      <!-- <div class="heart-img" @click="this.$router.push({ name: 'chat' })"> -->
      <div class="heart-img" @click="click">
        <img src="@/assets/main/heart.png" alt="" />
      </div>
      <div class="circle" style="animation-delay: 0s"></div>
      <div class="circle" style="animation-delay: 1s"></div>
      <div class="circle" style="animation-delay: 2s"></div>
      <div class="circle" style="animation-delay: 3s"></div>
    </div>

    <!-- 매칭이 눌렸을 때는 매칭 중이라고 띄우기-->
    <button class="matching-btn" v-if="matchBtn == false">
      매칭을 시작해주세요
    </button>
    <button class="matching-btn" v-if="matchBtn == true">매칭 중</button>
    <!--  -->
  </div>
</template>

<script>
import { useMainStore } from "@/stores/main/main";

export default {
  name: "ButtonList",
  components: {},
  data() {
    return {
      matchBtn: false,
    };
  },
  setup() {
    const main = useMainStore();
    return { main };
  },
  methods: {
    click() {
      //socket test
      // 1. 웹소켓 클라이언트 객체 생성
      const webSocket = new WebSocket("ws://localhost:8080/ws...");

      // 2. 웹소켓 이벤트 처리
      // 2-1) 연결 이벤트 처리
      webSocket.onopen = () => {
        console.log("웹소켓서버와 연결 성공");
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
#container {
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
    opacity: 0.5;
  }
  to {
    transform: scale(2.5, 2.5);
    opacity: 0;
  }
}

.heart-img {
  background-color: white;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  z-index: 100;
  padding: 5px;
  cursor: pointer;
}
.heart-img img {
  width: 50px;
  height: 50px;
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
.matching-btn:hover {
  background: #dcddfe;
  color: black;
  cursor: default;
}
.matching-btn:active {
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
</style>
