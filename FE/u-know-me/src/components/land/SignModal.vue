<template>
  <div class="sign-modal-bg">
    <div class="sign-modal">
      <div class="close-btn" @click="btnCh=0">
        <i class="fa-solid fa-xmark x-btn"></i>
      </div>
      <SignIn v-if="btnCh==1"/>
      <SignUp v-if="btnCh==2"/>
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
  height: 500px;
  background: #FFFFFF;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 27px;
  left:50%;
  top: 50%;
  transform: translate(-50%, -50%);
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