<template>
  <div class="chat-sub">
    <div class="chat-keyword-container">
      <div class="keyword">
        <div class="keyword-content">
          <p>"유노"님께서 입장하셨습니다.</p>
          <p>"유노"님께서 입장하셨습니다.</p>
          <p>"유노"님께서 입장하셨습니다.</p>
          <p>이것은 더미 데이터입니다. 길게 한 번 작성해보겠습니다.</p>
          <p>예쁘게 만들고 싶은데 디자인 솜씨가 부족한 걸까요.. 어렵네요.</p>
          <p>예쁘게 만들고 싶은데 디자인 솜씨가 부족한 걸까요.. 어렵네요. 더더더더더더 긴 문장입니다.</p>
        </div>
      </div>
    </div>

    <div class="chat-love-container">
      <div id="love-container">
        <div class="heart-img" @click="chat.heartClick(), love()">
          <img src="@/assets/main/heart.png" alt="" />
        </div>
        <div :class="{'circle' : this.success === false, 'success-circle' : this.success === true}" style="animation-delay: 0s"></div>
        <div :class="{'circle' : this.success === false, 'success-circle' : this.success === true}" style="animation-delay: 1s"></div>
        <div :class="{'circle' : this.success === false, 'success-circle' : this.success === true}" style="animation-delay: 2s"></div>
        <div :class="{'circle' : this.success === false, 'success-circle' : this.success === true}" style="animation-delay: 3s"></div>
      </div>
    </div>

    <div class="chat-icon-container">
      <div class="option-btn-list">
        <div class="option">
          <button class="chat-btn-lg" @click="chat.gameBtn = 1">
            <div>밸런스 게임</div>
            <img src="@/assets/chat/game-img.png" alt="">
          </button>
          <button class="chat-btn-lg" @click="chat.accuseBtn = 1">
            <div>신고하기</div>
            <img src="@/assets/chat/accuse-img.png" alt="">
          </button>
        </div>
        <div class="option">
          <button class="chat-btn-lg">
            <div>모션 인식</div>
            <img src="@/assets/chat/option-on-img.png" alt="" />
            <!-- <img src="@/assets/chat/option-off-img.png" alt="" /> -->
          </button>
          <button
            @click="chat.leaveSession(), $router.replace({ name: 'main' })"
            class="chat-btn-lg">
            <div>나가기</div>
            <img src="@/assets/chat/exit-img.png" alt="">
          </button>
        </div>
        <div class="logo">
          <img src="@/assets/chat/youknowme-img.png" alt="">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useChatStore } from "@/stores/chat/chat";

export default {
  setup() {
    const chat = useChatStore();
    return { chat, success: false };
  },
  methods: {
    love() {
      this.success = true
    }
  }
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
  place-items: center;
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
  background-color: #EBDCFE;
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
  color:black
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
.keyword {
  min-width: 150px;
  width: 500px;
  height: 70%;
  margin: auto 30px;
  background-color: #EBDCFE;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 20px;
  flex: 1;
  font-weight: 600;
  overflow: auto;
}
.keyword-content {
  margin: 2% 5%;
}
.keyword::-webkit-scrollbar {
  display: none;
}
.icon-list {
}
.logo {
  width: 100%;
  display: flex;
  justify-content: center;
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
</style>