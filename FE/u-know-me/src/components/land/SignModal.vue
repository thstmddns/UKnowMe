<template>
  <div class="sign-modal-bg">
    <div class="sign-modal" 
    :class="{
        'signin-modal': btnCh===1, 
        'signup-modal': btnCh===2,
        'sns-login-modal': btnCh===3,
        'find-id-modal': btnCh===4,
        'find-password-modal': btnCh===5,
        'find-id-result-modal': btnCh===6,
        'find-password-result-modal': btnCh===7,
      }">
      <div class="close-btn" @click="signModalClose()">
        <i class="fa-solid fa-xmark x-btn"></i>
      </div>
      <div
      :class="{
        'signin-modal-content': btnCh===1, 
        'signup-modal-content': btnCh===2,
        'sns-login-modal-content': btnCh===3,
        'find-id-modal-content': btnCh===4,
        'find-password-modal-content': btnCh===5,
        'find-id-result-modal-content': btnCh===6,
        'find-password-result-modal-content': btnCh===7,
      }">
        <SignIn v-if="btnCh===1"/>
        <SignUp v-if="btnCh===2"/>
        <SnsLogin v-if="btnCh===3"/>
        <FindId v-if="btnCh===4"/>
        <FindPassword v-if="btnCh===5"/>
        <FindIdResult v-if="btnCh===6"/>
        <FindPasswordResult v-if="btnCh===7"/>
      </div>
    </div>
  </div>
</template>

<script>
import SignIn from '@/components/land/modal/SignIn.vue'
import SignUp from '@/components/land/modal/SignUp.vue'
import SnsLogin from '@/components/land/modal/SnsLogin.vue'
import FindId from '@/components/land/modal/FindId.vue'
import FindIdResult from '@/components/land/modal/FindIdResult.vue'
import FindPassword from '@/components/land/modal/FindPassword.vue'
import FindPasswordResult from '@/components/land/modal/FindPasswordResult.vue'
import { storeToRefs } from 'pinia'
import { useLandStore } from '@/stores/land/land'
import { useAccountStore } from '@/stores/land/account'


export default {
  name: 'SignModal',
  components: {
    SignIn,
    SignUp,
    SnsLogin,
    FindId,
    FindPassword,
    FindIdResult,
    FindPasswordResult,
  },
  methods: {
    signModalClose() {
      this.btnCh = 0;
      this.account.checkSign = {
        id: 0,
        nickName: 0,
        tel: 0,
      },
      this.account.sendTel = 0
      this.account.authError.login = 0
    },
  },
  setup() {
    const land = useLandStore()
    const account = useAccountStore()
    const { btnCh } = storeToRefs(land)
    return {
      land,
      btnCh,
      account,
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
/* .signin-modal {
  height: 550px;
} */
.signup-modal {
  height: 92%;
}
.sns-login-modal {
  height: 550px;
}
.find-id-modal {
  height: 488px;
}
/* .find-id-result-modal {
  height: 488px;
} */
.find-password-modal {
  height: 620px;
}
/* .find-password-result-modal {
  height: 488px;
} */
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
.sns-login-modal-content {
  padding: 32px 69px;
  text-align: center;
  height: 92%;
}
.find-id-modal-content {
  padding: 32px 62px;
}
.find-id-result-modal-content {
  padding: 32px 62px;
}
.find-password-modal-content {
  padding: 32px 62px;
}
.find-password-result-modal-content {
  padding: 32px 62px;
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