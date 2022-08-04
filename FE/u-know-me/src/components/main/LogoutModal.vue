<template>
  <div class="logout-modal-bg">
    <div class="logout-modal">
      <div class="logout-modal-title">
        <h2 class="logout-title">로그아웃</h2>
        <div @click="logoutBtn = false">
          <i class="fa-solid fa-xmark x-btn"></i>
        </div>
      </div>
      
      <div class="logout-modal-content">
        <h3>로그아웃 하시겠습니까?</h3>
      </div>

      <div class="logout-btn-list">
        <button class="logout-btn">아니오</button>
        <button @click="$router.push({ name: 'home' })" class="logout-btn">예</button>
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
    const { logoutBtn } = storeToRefs(main);
    return {
      main,
      logoutBtn,
    };
  },
};
</script>

<style>
.logout-modal-bg {
  width: 100vw;
  height: 100vh;
  z-index: 9;
  background: rgba(0, 0, 0, 0.5);
  position: fixed;
}

.logout-modal {
  position: relative;
  width: 25%;
  height: 250px;
  background: #ffffff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 27px;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.logout-modal-title {
  margin: 0 8%;
  display: flex;
  justify-content: space-between;
}

.logout-title {
  float: left;
  color: #A056FF;
}

.logout-modal-content {
  padding: 10px 62px 25px ;
  text-align: center;
}

.logout-btn-list {
  display: flex;
  justify-content: center;
}

.logout-btn {
  width: 100px;
  height: 40px;
  background: #A056FF;
  color: white;
  box-shadow: 0px 2.95056px 2.21292px rgba(0, 0, 0, 0.25);
  border-radius: 7.37639px;
  border: none;
  margin: 0 20px;
  cursor: pointer;
}

.logout-btn:hover {
  transition: all 150ms linear;
  opacity: .85;
}
</style>
