<template>
  <!-- <h1>버튼 리스트</h1> -->
  <div class="chat-content-wrap">
    <div class="chat-content-left">
      <div class="sketch"></div>
    </div>

    <div class="chat-content-center">
      <div id="love-container">
        <div class="heart-img" @click="chat.heartClick()">
          <img src="@/assets/main/heart.png" alt="" />
        </div>
        <div class="circle" style="animation-delay: 0s"></div>
        <div class="circle" style="animation-delay: 1s"></div>
        <div class="circle" style="animation-delay: 2s"></div>
        <div class="circle" style="animation-delay: 3s"></div>
      </div>
    </div>

    <div class="chat-content-right">
      <div class="sketch"></div>
      <div class="chat-btn-list">
        <div class="top-btn">
          <button class="chat-btn sketch-btn">
            <i class="fa-solid fa-pencil"></i>
          </button>
          <button class="chat-btn camera-btn">
            <i class="fa-solid fa-camera"></i>
          </button>
          <button class="chat-btn-lg set-btn">
            옵션 <i class="fa-solid fa-gear"></i>
          </button>
        </div>
        <div class="bottom-btn">
          <button class="chat-btn accuse-btn">
            <i class="fa-solid fa-triangle-exclamation"></i>
          </button>
          <button class="chat-btn game-btn">
            <i class="fa-solid fa-gamepad"></i>
          </button>
          <button
            @click="$router.push({ name: 'main' })"
            class="chat-btn-lg out-btn"
          >
            나가기 <i class="fa-solid fa-arrow-right-from-bracket"></i>
          </button>
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
  min-width: 270px;
  margin: auto 20px;
}

.chat-btn {
  width: 60px;
  height: 60px;
  margin: 5px;
  background-color: white;
  border: 0;
  outline: 0;
  box-shadow: 0px 2.72109px 2.72109px rgba(0, 0, 0, 0.25);
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
</style>
