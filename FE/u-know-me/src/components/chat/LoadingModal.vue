<template>
  <div class="loading-modal-bg">
    <div class="loading-modal">
      <div class="loading-text-title">로딩 중</div>
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
.loading-text-title {
  width: 500px;
  position: absolute;
  left: 50%;
  top: 30%;
  font-size: 50px;
  transform: translate(-50%, -50%);
}
.loading-text {
  width: 500px;
  position: absolute;
  left: 50%;
  top: 50%;
  font-size: 20px;
  transform: translate(-50%, -50%);
}
</style>