<template>
  <div class="sign-modal-bg">
    <div class="sign-modal" 
    :class="{
        'signin-modal': btnCh===1, 
        'signup-modal': btnCh===2,
      }">
      <div class="close-btn" @click="btnCh=0">
        <i class="fa-solid fa-xmark x-btn"></i>
      </div>
      <div
      :class="{
        'signin-modal-content': btnCh===1, 
        'signup-modal-content': btnCh===2,
      }">
        <SignIn v-if="btnCh===1"/>
        <SignUp v-if="btnCh===2"/>
      </div>
    </div>
  </div>
</template>

<script>
import SignIn from '@/components/land/modal/SignIn.vue'
import SignUp from '@/components/land/modal/SignUp.vue'
import { storeToRefs } from 'pinia'
import { useLandStore } from '@/stores/land/land'

export default {
  name: 'SignModal',
  components: {
    SignIn,
    SignUp,
  },
  setup() {
    const land = useLandStore()
    const { btnCh } = storeToRefs(land)
    return {
      land,
      btnCh
    }
  },
}
</script>

<style>
.sign-modal-bg {
  width: 100vw;
  height:100vh;
  z-index: 1;
  box-sizing: border-box;
  background: rgba(255, 255, 255, 0.25);
  position: fixed;
  padding: 20px;
}
.sign-modal {
  position: relative;
  width: 550px;
  background: #FFFFFF;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 27px;
  left:50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.signin-modal {
  height: 550px;
}
.signup-modal {
  height: 92%;
}
.sign-modal-content {
  padding: 32px 62px;
}
.signin-modal-content {
  padding: 32px 69px;
  text-align: center;
  height: 92%;
}
.signup-modal-content {
  padding: 32px 62px;
  height: 92%;
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