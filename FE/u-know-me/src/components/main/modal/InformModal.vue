<template>
  <div class="inform-modal-bg">
    <div class="inform-modal">
      <div class="inform-modal-title">
        <h2 class="inform-title">기본정보 수정</h2>
        <div @click="informBtn = false">
          <i class="fa-solid fa-xmark x-btn"></i>
        </div>
      </div>
      
      <div class="inform-modal-content">
        <div>개인 정보 조회를 위해서는 인증이 필요합니다. <br>
          비밀번호를 입력 후 확인 버튼을 클릭해 주세요.</div>
      </div>
      <div class="password-input">
        <div>비밀번호</div>
        <input type="text" id="inform-password">
      </div>

      <div class="inform-button-list">
        <button class="inform-btn">확인</button>
      </div>

    </div>
  </div>
</template>

<script>
import { storeToRefs } from "pinia";
import { useMainStore } from "@/stores/main/main";

export default {
  name: "LogoutModal",
  components: {},
  setup() {
    const main = useMainStore();
    const { informBtn } = storeToRefs(main);
    return {
      main,
      informBtn,
    };
  },
};
</script>

<style>
/* .inform-modal-bg {
  width: 100vw;
  height: 100vh;
  z-index: 1;
  background: rgba(0, 0, 0, 0.5);
  position: fixed;
} */

.inform-modal {
  position: relative;
  width: 30%;
  height: 350px;
  background: #ffffff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 27px;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.inform-modal-title {
  margin: 0 8%;
  display: flex;
  justify-content: space-between;
}

.inform-title {
  float: left;
  color: #A056FF;
}

.inform-modal-content {
  padding: 0 62px;
  margin: 30px 0;
  text-align: center;
}

.password-input {
  margin: 40px 100px;
}

.inform-button-list {
  display: flex;
  justify-content: center;
}

.x-btn {
  position: absolute;
  right: 30px;
  top: 20px;
  font-size: 30px;
  cursor: pointer;
}

.inform-btn {
  width: 100px;
  height: 30px;
  background: #A056FF;
  color: white;
  box-shadow: 0px 2.95056px 2.21292px rgba(0, 0, 0, 0.25);
  border-radius: 7.37639px;
  border: none;
  margin: 0 20px;
  cursor: pointer;
}

.inform-btn:hover {
  transition: all 150ms linear;
  opacity: .85;
}

#inform-password {
  box-sizing: border-box;
  width: 100%;
  height: 40px;
  background: #FFFFFF;
  border: 1px solid #C1BBBB;
  border-radius: 8px;
  padding: 10px;
}
</style>