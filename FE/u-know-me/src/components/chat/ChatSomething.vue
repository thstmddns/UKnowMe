<template>
  <!-- <h1>버튼 리스트</h1> -->
  <div class="chat-content-wrap">
    <div class="chat-content-left">
      <div class="sketch"></div>
    </div>

    <div class="chat-content-center">
      <div id="love-container">
        <div class="heart-img" @click="chat.heartClick()" :class="chat-success">
          <img src="@/assets/main/heart.png" alt="" />
        </div>
        <div class="circle" style="animation-delay: 0s"></div>
        <div class="circle" style="animation-delay: 1s"></div>
        <div class="circle" style="animation-delay: 2s"></div>
        <div class="circle" style="animation-delay: 3s"></div>
      </div>
    </div>

    <div class="chat-content-right">
      <div class="chat-btn-list">
        <div class="popup-wrapper">
          <div class="popup accuse-popup">
            <div class="tooltip">비매너 행위를 신고하세요!</div>
            <button class="chat-btn accuse-btn">
              <img class="accuse-box-img" src="@/assets/chat/accuse-img.png" alt="">
            </button>
          </div>
          <div class="popup balance-popup">
            <div class="tooltip">밸런스 게임으로 상대를 알아가는 건 어떨까요?</div>
            <button class="chat-btn game-btn">
              <img src="@/assets/chat/game-img.png" alt="">
            </button>
          </div>
        </div>
        <div>
          <div>
            <button class="chat-btn-lg set-btn">
              <img src="@/assets/chat/option-img.png" alt="" />
            </button>
          </div>
          <div>
            <button
              @click="chat.leaveSession(), $router.replace({ name: 'main' })"
              class="chat-btn-lg out-btn">
              <img src="@/assets/chat/exit-img.png" alt="">
            </button>
          </div>
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

    return { chat };
  },
};
</script>

<style>
.flex {
  display: flex;
}
.chat-content-wrap {
  display: flex;
  height: max-content;
}
.chat-content-left {
  min-width: 400px;
  height: 200px;
  /* background-color: blanchedalmond; */
  display: flex;
  flex: 1;
}
.chat-content-right {
  min-width: 400px;
  height: 200px;
  /* background-color: blanchedalmond; */
  display: flex;
  flex: 1;
}
.chat-content-center {
  min-width: 200px;
  height: 200px;
  display: grid;
  place-items: center;
  overflow: hidden;
  position: relative;
}
.love-btn {
  width: 200px;
  height: 200px;
  display: grid;
  place-items: center;
  overflow: hidden;
  position: relative;
}
.chat-guide {
  width: 40%;
  height: 200px;
  /* background-color: blanchedalmond; */
}
.chat-text {
  width: 100%;
  height: 70%;
  margin: auto 20px;
  background-color: white;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 20px;
  margin-top: 32px;
}
.sketch {
  min-width: 150px;
  height: 70%;
  margin: auto 20px;
  background-color: white;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 20px;
  flex: 1;
}

.chat-btn-list {
  /* min-width: 270px; */
  margin: auto 20px;
  display: flex;
}

.chat-btn {
  width: 60px;
  height: 60px;
  margin: 5px;
  padding: 0;
  background-color: transparent;
  border: 0;
  outline: 0;
  box-shadow: none;
  border-radius: 20.4082px;
  cursor: pointer;
}
.chat-btn-lg {
  width: 120px;
  height: 60px;
  margin: 5px;
  background-color: white;
  border: 0;
  outline: 0;
  box-shadow: 0px 2.72109px 2.72109px rgba(0, 0, 0, 0.25);
  border-radius: 20.4082px;
  cursor: pointer;
}


.popup-wrapper {
  display: inline-flex;
}
.popup-wrapper .pop-up {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  position: relative;
}
.pop-up {
  
}
.pop-up div {
}
.accuse-btn {
  
}
.popup-wrapper .popup .tooltip {
  background-color: pink;
  border-radius: 25px;
  padding: 5px 10px;
  font-size: 15px;
  opacity: 0;
  pointer-events: none;
}
.popup-wrapper .popup:hover .tooltip {
  opacity: 1;
  pointer-events: auto;
}
.popup-wrapper .popup .tooltip::before {
}

.accuse-btn img {
  width: 40px;
  height: 40px;
}
.game-btn img {
  width: 40px;
  height: 40px;
  margin-left: 15%;
}


.set-btn img {
  width: 40px;
  height: 40px;
}
.out-btn img {
  width: 40px;
  height: 40px;
}
</style>
