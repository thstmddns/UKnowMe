<template>
  <div class="main-modal-bg">
    <div class="inform-side-btn" v-if="main.pBtnCh">
      <div><button class="inform-modify-btn" :class="{'inform-click-btn':main.pBtnCh===1}" @click="main.pBtnCh=1">내프로필</button></div>
      <div><button class="inform-modify-btn" :class="{'inform-click-btn':main.pBtnCh===2}" @click="main.pBtnCh=2">보안설정</button></div>
    </div>
    <div class="main-modal" 
    :class="{
        'logout-modal': btnCh===1, 
        'inform-password-modal': btnCh===2,
        'inform-modal': btnCh===3,
        'inform-modify-profile': btnCh===3&&main.pBtnCh===1,
        'inform-modify-secure': btnCh===3&&main.pBtnCh===2,
      }">
      <div class="close-btn" @click="btnCh=0;main.pBtnCh=0">
        <i class="fa-solid fa-xmark x-btn"></i>
      </div>
      <div
      :class="{
        'logout-modal-content': btnCh===1, 
        'inform-password-modal-content': btnCh===2,
        'inform-modal-content': btnCh===3,
        'inform-modify-profile-content': btnCh===3&&main.pBtnCh===1,
        'inform-modify-secure-content': btnCh===3&&main.pBtnCh===2,
      }">
        <Logout v-if="btnCh===1"/>
        <InformPassword v-if="btnCh===2"/>
        <Inform v-if="btnCh===3"/>
      </div>
    </div>
  </div>
</template>

<script>
import Inform from '@/components/main/modal/InformModal.vue'
import InformPassword from '@/components/main/modal/InformPassword.vue'
import Logout from '@/components/main/modal/LogoutModal.vue'
import { storeToRefs } from 'pinia'
import { useMainStore } from '@/stores/main/main'

export default {
  name: 'MainModal',
  components: {
    Inform,
    InformPassword,
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
  background: #ffffff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 27px;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.inform-side-btn {
  position: absolute;
  left: calc(50% - 412px);
  top: 20%;
}
.logout-modal {
  width: 440px;
  height: 240px;
}
.inform-modal {
  width: 640px;
  /* height: 80%; */
}
.inform-password-modal {
  width: 550px;
  /* height: 350px; */
}
.inform-modify-profile {
  height: 80%;
}
.inform-modify-secure {
  height: 80%;
}
.logout-modal-content {
  padding: 32px 62px;
  text-align: left;
}
.inform-modal-content {
  padding: 32px 69px;
  text-align: left;
  height: 92%;
}
.inform-password-modal-content {
  padding: 32px 69px;
  text-align: left;
  height: 92%;
}
.inform-modify-profile-content {
  padding: 32px 69px;
  text-align: left;
  height: 92%;
}
.inform-modify-secure-content {
  padding: 32px 69px;
  text-align: left;
  height: 92%;
}
.inform-modify-btn {
  margin: 4px 0;
  background-color: #A056FF;
  color: white;
}
.inform-click-btn {
  position: relative;
  left: -12%;
}
</style>