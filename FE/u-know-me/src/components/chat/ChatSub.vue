<template>
  <div style="height: var(--chat-sub-size)"><!-- 형상 유지를 위한 div --></div>
  <div class="chat-sub">
    <div class="chat-keyword-container">
      <div class="keyword-box">
        <div class="keyword-content"></div>
      </div>
    </div>

    <div class="chat-love-container">
      <div id="love-container">
        <div class="heart-img" @click="love()">
          <img class="heart-img-src" src="@/assets/main/heart.png" alt="" />
        </div>
        <div
          :class="{
            circle: this.success === false,
            'success-circle': this.success === true,
          }"
          style="animation-delay: 0s"
        ></div>
        <div
          :class="{
            circle: this.success === false,
            'success-circle': this.success === true,
          }"
          style="animation-delay: 1s"
        ></div>
        <div
          :class="{
            circle: this.success === false,
            'success-circle': this.success === true,
          }"
          style="animation-delay: 2s"
        ></div>
        <div
          :class="{
            circle: this.success === false,
            'success-circle': this.success === true,
          }"
          style="animation-delay: 3s"
        ></div>
      </div>
    </div>

    <div class="chat-icon-container">
      <div class="option-btn-list">
        <div class="option">
          <button
            class="chat-btn-lg"
            @click="chat.balanceClick(), (chat.gameBtn = 1)"
          >
            <img src="@/assets/chat/game-img.png" alt="" />
            <div>밸런스 게임</div>
          </button>
          <button class="chat-btn-lg" @click="chat.accuseBtn = 1">
            <img src="@/assets/chat/accuse-img.png" alt="" />
            <div>신고하기</div>
          </button>
        </div>
        <div class="option">
          <button class="chat-btn-lg motionBtn" @click="chat.motionClick()">
            <img
              src="@/assets/chat/option-on-img.png"
              alt=""
              v-if="chat.motionCheck == true"
            />
            <img
              src="@/assets/chat/option-off-img.png"
              alt=""
              v-if="chat.motionCheck == false"
            />
            <div>모션 인식</div>
          </button>
          <button
            @click="chat.leaveSession()"
            class="chat-btn-lg"
          >
            <img src="@/assets/chat/exit-img.png" alt="" />
            <div>나가기</div>
          </button>
        </div>
        <div class="logo">
          <img src="@/assets/chat/youknowme-img.png" alt="" />
        </div>
      </div>
    </div>
  </div>
  <div class="chat-sub-mobile">
    <div class="chat-keyword-container">
      <div class="keyword-box">
        <div class="keyword-content-mobile" @click="chatSubMobileClick()"></div>
      </div>
    </div>
    <div class="chat-sub-mobile-child">
      <div class="chat-left-container" style="min-width: 160px; height: 300px">
        <div class="option-btn-list" style="margin: auto 0px">
          <div class="option" style="margin-left: auto; margin-right: 0">
            <button class="chat-btn-lg-mobile" @click="chat.balanceClick()">
              <img src="@/assets/chat/game-img.png" alt="" />
              <div>밸런스 게임</div>
            </button>
            <button class="chat-btn-lg-mobile" @click="chat.accuseBtn = 1">
              <img src="@/assets/chat/accuse-img.png" alt="" />
              <div>신고하기</div>
            </button>
          </div>
        </div>
      </div>

      <div class="chat-love-container" style="min-width: 200px; height: 300px">
        <div id="love-container">
          <div class="heart-img" @click="love()">
            <img class="heart-img-src" src="@/assets/main/heart.png" alt="" />
          </div>
          <div
            :class="{
              circle: this.success === false,
              'success-circle': this.success === true,
            }"
            style="animation-delay: 0s"
          ></div>
          <div
            :class="{
              circle: this.success === false,
              'success-circle': this.success === true,
            }"
            style="animation-delay: 1s"
          ></div>
          <div
            :class="{
              circle: this.success === false,
              'success-circle': this.success === true,
            }"
            style="animation-delay: 2s"
          ></div>
          <div
            :class="{
              circle: this.success === false,
              'success-circle': this.success === true,
            }"
            style="animation-delay: 3s"
          ></div>
        </div>
      </div>

      <div class="chat-right-container" style="min-width: 160px; height: 300px">
        <div class="option-btn-list" style="margin: auto 0px">
          <div class="option" style="margin-left: 0; margin-right: auto">
            <button
              class="chat-btn-lg-mobile motionBtn"
              @click="chat.motionClick()"
            >
              <img
                src="@/assets/chat/option-on-img.png"
                alt=""
                v-if="chat.motionCheck == true"
              />
              <img
                src="@/assets/chat/option-off-img.png"
                alt=""
                v-if="chat.motionCheck == false"
              />
              <div>모션 인식</div>
            </button>
            <button
              @click="chat.leaveSession(), $router.replace({ name: 'main' })"
              class="chat-btn-lg-mobile"
            >
              <img src="@/assets/chat/exit-img.png" alt="" />
              <div>나가기</div>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useChatStore } from "@/stores/chat/chat";
import { onMounted } from "vue";
import { useMainStore } from "@/stores/main/main";

export default {
  data() {
    return {
      success: false,
      chatExpand: false,
    };
  },
  setup() {
    const chat = useChatStore();
    const main = useMainStore();

    onMounted(() => {
      var chatSub = document.querySelector(".chat-sub");
      var chatSubMobile = document.querySelector(".chat-sub-mobile");
      setTimeout(() => {
        // 접속당시 width가 1120px 보다 작을 때,
        console.log("window.innerWidth!!!", window.innerWidth);
        if (window.innerWidth < 1120) {
          chatSub.style.bottom = "-200px";
          chatSubMobile.style.left = "50%";
          document.documentElement.style.setProperty(
            "--chat-sub-size",
            "400px"
          );
          // 1대1일때 모바일
          if (main.option.matchingRoom == "1") {
            document.documentElement.style.setProperty("--video-size", "1");
            chat.mobile = true;

            document.getElementById("avatarCanvas1").id = "avatarCanvas2";
          }
        }
      }, 2000);
      //media 반응형
      const mediaViewContent = window.matchMedia(`(max-width: 1120px)`); // 1
      const viewChangeHandler = (mediaViewContent) => {
        // 모바일
        if (mediaViewContent.matches === true) {
          chatSub.style.bottom = "-200px";
          chatSubMobile.style.left = "50%";
          document.documentElement.style.setProperty(
            "--chat-sub-size",
            "400px"
          );
          if (main.option.matchingRoom == "1") {
            document.documentElement.style.setProperty("--video-size", "1");
            chat.mobile = true;

            document.getElementById("avatarCanvas1").id = "avatarCanvas2";
          }
        } else {
          chatSub.style.bottom = "0px";
          chatSubMobile.style.left = "-300px";
          document.documentElement.style.setProperty(
            "--chat-sub-size",
            "200px"
          );
          if (main.option.matchingRoom == "1") {
            document.documentElement.style.setProperty("--video-size", "2");
            chat.mobile = false;

            document.getElementById("avatarCanvas2").id = "avatarCanvas1";
          }
        }
      };
      mediaViewContent.addEventListener("change", viewChangeHandler);
    });

    return { chat, main };
  },
  methods: {
    love() {
      if (!this.success) {
        this.chat.heartClick();
        this.success = true;
        var heartBtn = document.querySelectorAll(".heart-img");
        var heartImg = document.querySelectorAll(".heart-img-src");

        for (let i = 0; i < heartBtn.length; i++) {
          heartBtn[i].style.width = "100px";
          heartBtn[i].style.height = "100px";
          heartImg[i].style.animationDuration = "1s";
          heartImg[i].style.animationName = "heartScaleIn";
          heartImg[i].style.animationIterationCount = "infinite";
        }
      }
    },
    chatSubMobileClick() {
      if (!this.chatExpand) {
        this.chatExpand = true;
        document.querySelector(".keyword-content-mobile").style.height =
          "calc(100vh - 400px)";
      } else {
        this.chatExpand = false;
        document.querySelector(".keyword-content-mobile").style.height = "50px";
      }
    },
  },
};
</script>

<style>
/* 전역변수 */
:root {
  /* 비디오사이즈 1=모바일 2=데탑 */
  --video-size: 2;
  /* 하단바 사이즈 */
  --chat-sub-size: 200px;
}

.chat-sub {
  position: absolute;
  display: flex;
  width: 100%;
  min-width: 10px;
  height: max-content;
  bottom: 0px;
  transition: 0.5s;
}
/* 모바일 */
.chat-sub-mobile {
  position: absolute;
  display: flex;
  bottom: 0px;
  left: 50%;
  flex-direction: column;
  height: max-content;
  transform: translate(-50%, 0%);
  transition: 0.5s;
}
.chat-sub-mobile-child {
  display: flex;
  height: max-content;
}
@media screen and (min-width: 1120px) {
  .chat-sub {
    bottom: 0px;
  }
  .chat-sub-mobile {
    left: -300px;
  }
  :root {
    --chat-sub-size: 200px;
  }
}
/* 모바일 */
@media screen and (max-width: 1120px) {
  .chat-sub {
    bottom: -200px;
  }
  .chat-sub-mobile {
    left: 50%;
  }
  :root {
    --chat-sub-size: 400px;
  }
}
.chat-keyword-container {
  display: flex;
  min-width: 400px;
  height: 200px;
  flex: 1;
}
.chat-love-container {
  position: relative;
  display: flex;
  text-align: center;
}
.chat-icon-container {
  display: flex;
  flex-direction: column;
  min-width: 400px;
  height: 200px;
  flex: 1;
}

/* 모바일 */
.chat-left-container {
  display: flex;
  flex-direction: column;
  min-width: 400px;
  height: 200px;
  flex: 1;
}
.chat-right-container {
  display: flex;
  flex-direction: column;
  min-width: 400px;
  height: 200px;
  flex: 1;
}

.option-btn-list {
  min-width: 150px;
  height: 70%;
  margin: auto 30px;
  display: flex;
}
.option {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  margin-right: 20px;
}
.chat-btn-lg {
  width: 190px;
  height: 60px;
  padding: 10px 15px;
  background-color: #ebdcfe;
  border: 0;
  outline: 0;
  box-shadow: 0px 2.72109px 2.72109px rgba(0, 0, 0, 0.25);
  border-radius: 20.4082px;
  cursor: pointer;
  display: flex;
  justify-content: space-between;
}
.chat-btn-lg-mobile {
  width: 150px;
  height: auto;
  padding: 10px 15px;
  background-color: #ebdcfe;
  border: 0;
  outline: 0;
  box-shadow: 0px 2.72109px 2.72109px rgba(0, 0, 0, 0.25);
  border-radius: 20.4082px;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.chat-btn-lg:hover,
.chat-btn-lg-mobile:hover {
  background-color: #d5b6ff;
  color: black;
}
.chat-btn-lg div,
.chat-btn-lg-mobile div {
  width: 120px;
  height: 30px;
  font-size: 20px;
  font-weight: 600;
  line-height: 40px;
}
.chat-btn-lg img,
.chat-btn-lg-mobile img {
  margin: auto;
  width: 40px;
  height: 40px;
}
.keyword-box {
  min-width: 150px;
  width: 500px;
  height: 70%;
  margin: auto 30px;
  background-color: #ebdcfead;
  backdrop-filter: blur(5px);
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 20px;
  flex: 1;
  font-weight: 600;
  overflow: auto;
}
.keyword-content,
.keyword-content-mobile {
  margin: 2% 5%;
  transition: 0.5s;
}
.keyword-content-mobile {
  height: 50px;
}
.keyword-box::-webkit-scrollbar {
  width: 10px;
}
.keyword-box::-webkit-scrollbar-thumb {
  background: #c1c3fc;
  border-radius: 10px;
}
.logo {
  width: 100%;
  display: flex;
  justify-content: center;
  transition: 0.5s;
}
@media screen and (max-width: 1450px) {
  .logo {
    opacity: 0;
  }
}
.logo img {
  width: auto;
  height: 100%;
}
.success-circle {
  border-radius: 50%;
  width: 80px;
  height: 80px;
  background-color: red;
  position: absolute;
  opacity: 0;

  animation: scaleIn 4s infinite cubic-bezier(0.36, 0.11, 0.89, 0.32);
}
@keyframes heartScaleIn {
  0% {
    transform: translate(-50%, -50%) scale(1);
  }
  50% {
    transform: translate(-50%, -50%) scale(1.5);
  }
  100% {
    transform: translate(-50%, -50%) scale(1);
  }
}
</style>