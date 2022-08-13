<template>
  <div class="game-modal-bg">
    <div class="game-modal">
      <div class="close-btn" @click="gameBtn = 0">
        <i class="fa-solid fa-xmark x-btn"></i>
      </div>
      <div class="game-modal-container">
        <div class="game-title">제목</div>
        <div class="game-content">
          <div class="answer">답1</div>
          <div class="answer">답2</div>
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
  /* background: rgba(0, 0, 0, 0.5); */
  position: fixed;
  top: 0;
  left: 0;
}

.game-modal {
  position: relative;
  width: 500px;
  height: 350px;
  background-color: rgba(0, 0, 0, 0.2);
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 27px;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.game-modal-container {
  padding: 32px 40px;
}
.game-title {
  width: 85%;
  height: 100px;
  display: flex;
  justify-content: center;
  margin: auto;
  background-color: rgba(255, 255, 255, 0.8);

}
.game-content {
  display: flex;
  justify-content: space-evenly;
  margin: 20px 0;
}
.answer {
  background-color: rgba(255, 255, 255, 0.8);
  width: 40%;
  height: 100px;
  text-align: center;
}
</style>
