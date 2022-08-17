<template>
  <div class="game-modal-bg">
    <div class="game-modal">
      <div class="close-btn" @click="chat.balanceAnswerClick('선택 포기')">
        <i class="fa-solid fa-xmark x-btn"></i>
      </div>
      <div class="game-modal-container">
        <div class="game-title">밸런스 게임</div>
        <div class="game-content">
          <button class="answser" @click="chat.balanceAnswerClick(chat.gameA1)">{{ chat.gameA1 }}</button>
          <img class="game-img" src="@/assets/chat/vs.png" alt="" />
          <button class="answser" @click="chat.balanceAnswerClick(chat.gameA2)">{{ chat.gameA2 }}</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { storeToRefs } from "pinia";
import { useChatStore } from "@/stores/chat/chat";

export default {
  name: "GameModal",
  components: {},
  setup() {
    const chat = useChatStore();
    const { gameBtn } = storeToRefs(chat);

    return {
      chat,
      gameBtn,
    };
  },
};
</script>

<style>
.game-modal-bg {
  width: 100vw;
  height: 100vh;
  z-index: 9;
  background: rgba(0, 0, 0, 0.5);
  position: fixed;
  top: 0;
  left: 0;
}

.game-modal {
  position: relative;
  width: 500px;
  min-height: 350px;
  background: #ffffff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 27px;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.game-modal-container {
  display: flex;
  flex-direction: column;
  padding: 32px 40px;
}
.game-title {
  padding: 2% 11%;
  text-align: center;
  font-size: 32px;
  font-weight: 700;
  color: #8227fa;
}
.game-content {
  display: flex;
  flex: 1;
  flex-direction: column;
  align-content: center;
}
.answser {
  flex: 1;
  width: 100%;
  height: 100px;
  font-size: 16px;
  text-align: center;
  margin-top: 20px;
  margin-bottom: 20px;
}
.game-img {
  width: 75px;
  margin: auto;
}
</style>
