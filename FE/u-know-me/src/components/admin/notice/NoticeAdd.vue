<template>
  <div class="notice-add-modal-bg">
    <div class="notice-add-modal">
      <div class="notice-add-modal-title">
        <h2 class="notice-add-title">로그아웃</h2>
        <div @click="noticeAddBtn = false">
          <i class="fa-solid fa-xmark x-btn"></i>
        </div>
      </div>
      
      <div class="notice-add-modal-content">
        <h3>로그아웃 하시겠습니까?</h3>
      </div>

      <div class="notice-add-btn-list">
        <button class="notice-add-btn">아니오</button>
        <button class="notice-add-btn">예</button>
      </div>

    </div>
  </div>
</template>

<script>
import { storeToRefs } from "pinia";
import { useAdminStore } from "@/stores/admin/admin";

export default {
  name: "NoticeAdd",
  components: {},
  setup() {
    const admin = useAdminStore();
    const { noticeAddBtn } = storeToRefs(admin);
    return {
      admin,
      noticeAddBtn,
    };
  },
};
</script>

<style>
.notice-add-modal-bg {
  width: 100vw;
  height: 100vh;
  z-index: 9;
  background: rgba(0, 0, 0, 0.5);
  position: fixed;
}

.notice-add-modal {
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

.notice-add-modal-title {
  margin: 0 8%;
  display: flex;
  justify-content: space-between;
}

.notice-add-title {
  float: left;
  color: #A056FF;
}

.notice-add-modal-content {
  padding: 10px 62px 25px ;
  text-align: center;
}

.notice-add-btn-list {
  display: flex;
  justify-content: center;
}

.notice-add-btn {
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

.notice-add-btn:hover {
  transition: all 150ms linear;
  opacity: .85;
}
</style>