<template>
  <div class="chat-sub">
    <div class="chat-keyword-container">
      <div class="keyword-box">
        <div class="keyword-content">
          <p>테스트</p>
        </div>
      </div>
    </div>

    <div class="chat-love-container">
      <div id="love-container">
        <div class="heart-img" @click="chat.heartClick(), love()">
          <img id="heart-img-src" src="@/assets/main/heart.png" alt="" />
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
            <div>밸런스 게임</div>
            <img src="@/assets/chat/game-img.png" alt="" />
          </button>
          <button class="chat-btn-lg" @click="chat.accuseBtn = 1">
            <div>신고하기</div>
            <img src="@/assets/chat/accuse-img.png" alt="" />
          </button>
        </div>
        <div class="option">
          <button class="chat-btn-lg" id="motionBtn" @click="chat.motionClick()">
            <div>모션 인식</div>
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
          </button>
          <button
            @click="chat.leaveSession(), $router.replace({ name: 'main' })"
            class="chat-btn-lg"
          >
            <div>나가기</div>
            <img src="@/assets/chat/exit-img.png" alt="" />
          </button>
        </div>
        <div class="logo">
          <img src="@/assets/chat/youknowme-img.png" alt="" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useChatStore } from "@/stores/chat/chat";
import { onMounted } from "vue";

export default {
  data() {
    return {
      success: false,
    };
  },
  setup() {
    const chat = useChatStore();
    var mobile = false;

    onMounted(() => {
      //media 반응형
      const mediaViewContent = window.matchMedia(`(max-width: 1120px)`); // 1
      const viewChangeHandler = (mediaViewContent) => {
        var toggleBtn = document.getElementById("avatarBtn");
        var toggle = document.querySelector(".avatarCollection");

        if (mediaViewContent.matches === true) {
          toggle.style.left = "-300px";
          toggleBtn.style.bottom = "50px";
        } else {
          toggle.style.left = "0px";
          toggleBtn.style.bottom = "-70px";
        }
      };
      mediaViewContent.addEventListener("change", viewChangeHandler);
    });

    return { chat, mobile };
  },
  methods: {
    love() {
      this.success = true;
      var heartBtn = document.querySelector(".heart-img");
      heartBtn.style.width = "100px";
      heartBtn.style.height = "100px";
      document.getElementById("heart-img-src").style.animationDuration = "1s";
      document.getElementById("heart-img-src").style.animationName =
        "heartScaleIn";
    },
  },
};
</script>

<style>
.chat-sub {
  display: flex;
  height: max-content;
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
.chat-btn-lg:hover {
  background-color: #d5b6ff;
  color: black;
}
.chat-btn-lg div {
  width: 120px;
  height: 40px;
  font-size: 20px;
  font-weight: 600;
  line-height: 40px;
}
.chat-btn-lg img {
  width: 40px;
  height: 40px;
}
.keyword-box {
  min-width: 150px;
  width: 500px;
  height: 70%;
  margin: auto 30px;
  background-color: #ebdcfe;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 20px;
  flex: 1;
  font-weight: 600;
  overflow: auto;
}
.keyword-content {
  margin: 2% 5%;
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