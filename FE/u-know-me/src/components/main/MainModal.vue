<template>
  <div class="main-modal-bg">
    <div class="main-modal" 
    :class="{
        'logout-modal': btnCh===1, 
        'inform-modal': btnCh===2,
      }">
      <div class="close-btn" @click="btnCh=0">
        <i class="fa-solid fa-xmark x-btn"></i>
      </div>
      <div
      :class="{
        'logout-modal-content': btnCh===1, 
        'inform-modal-content': btnCh===2,
      }">
        <Logout v-if="btnCh===1"/>
        <Inform v-if="btnCh===2"/>
      </div>
    </div>
  </div>
</template>

<script>
import Inform from '@/components/main/modal/InformModal.vue'
import Logout from '@/components/main/modal/LogoutModal.vue'
import { storeToRefs } from 'pinia'
import { useMainStore } from '@/stores/main/main'

export default {
  name: 'MainModal',
  components: {
    Inform,
    Logout,
  },
  setup() {
    const main = useMainStore()
    const { btnCh } = storeToRefs(main)
    return {
      main,
      btnCh
    }
  },
}
</script>

<style>
.main-modal-bg {
  width: 100vw;
  height: 100vh;
  z-index: 9;
  background: rgba(0, 0, 0, 0.5);
  position: fixed;
}
.main-modal {
  position: relative;
  width: 440px;
  background: #ffffff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 27px;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.logout-modal {
  height: 240px;
}
.inform-modal {
  height: 92%;
}
.logout-modal-content {
  padding: 32px 62px;
  text-align: left;
}
.inform-modal-content {
  padding: 32px 69px;
  text-align: center;
  height: 92%;
}
</style>