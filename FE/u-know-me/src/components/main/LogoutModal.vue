<template>
  <div class="modal-bg">
    <div class="logout-modal">
      <button @click="logoutBtn=false">닫기</button>
      <h4>로그아웃 모달</h4>

    </div>

  </div>
</template>

<script>
import { storeToRefs } from 'pinia'
import { useMainStore } from '@/stores/main/main'

export default {
  name: 'LogoutModal',
  components: {
  },
  setup() {
    const main = useMainStore()
    const { logoutBtn } = storeToRefs(main)
    return {
      main,
      logoutBtn
    }
  },
}
</script>

<style>
.modal-bg {
  width: 100vw;
  height:100vh;
  z-index: 1;
  background: rgba(0, 0, 0, 0.5);
  position: fixed;
  padding: 20px;
}

.logout-modal {
  width: 100%;
  background: white;
  border-radius: 27px;
  padding: 20px;
}

.close-btn {
  position: absolute;
  right: 20px;
  top: 20px;
}
.x-btn {
  font-size: 48px;
  cursor: pointer;
}
</style>