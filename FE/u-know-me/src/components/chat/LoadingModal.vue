<template>
  <div class="loading-modal-bg">
    <div class="loading-modal">
      <img class="loading-img" src="@/assets/chat/loding_anim_r.gif" alt="ani">
      <div class="loading-text-title">
        <span>LOADING</span>
      </div>
      <div class="loading-text">{{ chat.loadingText }}</div>
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
.loading-modal-bg {
  width: 100vw;
  height: 100vh;
  z-index: 9;
  background: rgba(0, 0, 0, 0.5);
  position: fixed;
  top: 0;
  left: 0;
  backdrop-filter: blur(5px);
}
.loading-modal {
  position: absolute;
  /* background-color: gray;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 27px; */
  left: 50%;
  top: 45%;
  transform: translate(-50%, -50%);
  text-align: center;
}
.loading-img {
  position: relative;
  width: 320px;
}
.loading-text-title {
  font-size: x-large;
  color: white;
}
.loading-text-title span {
  display: inline-block;
  margin: 0 -0.05em;
  animation: loading 0.5s infinite alternate;
}

loading-text-title span:nth-child(3) {
  animation-delay: 0.2s;
}

loading-text-title span:nth-child(4) {
  animation-delay: 0.3s;
}

loading-text-title span:nth-child(5) {
  animation-delay: 0.4s;
}

loading-text-title span:nth-child(6) {
  animation-delay: 0.5s;
}

loading-text-title span:nth-child(7) {
  animation-delay: 0.6s;
}

@keyframes loading-text-title {
  0% {
    opacity: 1;
  }

  100% {
    opacity: 0;
  }
}
/*  */

.loading-text {
  margin-top: 52px;
  font-size: 20px;
  color: white;
}
</style>