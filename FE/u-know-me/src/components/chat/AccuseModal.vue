<template>
  <div class="accuse-modal-bg">
    <div class="accuse-modal">
      <div class="close-btn" @click="accuseBtn = 0">
        <i class="fa-solid fa-xmark x-btn"></i>
      </div>
      <div class="accuse-modal-container">
        <div class="accuse-modal-title">신고하기</div>
        <div class="accuse-modal-content">
          상대의 비매너행위* 시 신고를 하실 수 있습니다. <br>
          허위 신고로 판단될 경우 적절한 조치가 있을 예정 <br>
          신고하시겠습니까? 
        </div>
        <div class="accuse-modal-content-sub">(비매너행위* : 욕설, 혐오 발언, 부적절한 행동)</div>
        <div class="accuse-btn-list">
          <button class="accuse-btn">신고</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { storeToRefs } from "pinia"
import { useChatStore } from "@/stores/chat/chat"

export default {
  name: "AccuseModal",
  components: {
  },
  setup() {
    const chat = useChatStore()
    const { accuseBtn } = storeToRefs(chat)

    return {
      chat,
      accuseBtn,
    };
  },
}
</script>

<style>
.accuse-modal-bg {
  width: 100vw;
  height: 100vh;
  z-index: 9;
  background: rgba(0, 0, 0, 0.5);
  position: fixed;
  top: 0;
  left: 0;
}

.accuse-modal {
  position: relative;
  width: 440px;
  height: 350px;
  background: #ffffff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 27px;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.accuse-modal-container {
  padding: 32px 40px;
  /* display: inline; */
}
.accuse-modal-title {
  padding: 2% 11%;
  text-align: center;
  font-size: 32px;
  font-weight: 700;
  color: #8227fa;
}
.accuse-modal-content {
  margin: 6% 0;
  text-align: center;
  line-height: 30px;
}
.accuse-modal-content-sub {
  font-weight: 700;
  text-align: center;
}
.accuse-btn-list {
  display: flex;
  justify-content: center;
  margin-top: 6%;
}
.accuse-btn {
  font-size: 15px;
  font-weight: 700;
}
.accuse-btn:hover {
  background-color: rgb(255, 51, 0);
  border: none;
}
</style>