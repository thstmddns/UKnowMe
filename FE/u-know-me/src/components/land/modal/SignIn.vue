<template>
  <div class="sign-head signin-head">
    <div>너, 나 알아?</div>
    <div style="font-size:16px;">Welcome Back</div>
  </div>
  <form id="signInForm" action="POST" @submit.prevent="account.login(credentials)">
    <div class="signin-input">
      <div><label for="signInId">아이디</label></div>
      <div><input type="text" name="signInId" id="signInId" v-model="credentials.id"/></div>
    </div>
    <div class="signin-input">
      <div><label for="signInPassword">비밀번호</label></div>
      <div><input type="text" name="signInPassword" id="signInPassword" v-model="credentials.password"/></div>
    </div>
    <div class="find-id-password">
      <span>아이디/</span><span>비밀번호 찾기</span>
    </div>
    <div class="flex justify-space-between align-center">
      <div class="flex">
        <img class="sns-login" src="@/assets/land/naver_login_icon.png" alt="naver_login_icon">
        <img class="sns-login" src="@/assets/land/google_login_icon.png" alt="google_login_icon">
        <img class="sns-login" src="@/assets/land/kakao_login_icon.png" alt="kakao_login_icon">
      </div>
      <p class="p-0">
        <span @click="land.btnCh=2">계정이 필요하신가요?</span>
      </p>
    </div>
    <button class="login-btn">로그인</button>
  </form>
</template>

<script>
import { ref } from 'vue'
import { useAccountStore } from '@/stores/land/account'
import { useLandStore } from '@/stores/land/land'

export default {
  name: 'SignIn',
  setup() {
    const account = useAccountStore()
    const land = useLandStore()
    const credentials = ref({
      id: '',
      password: '',
    })
    return {
      account,
      credentials,
      land,
    }

  }
}
</script>

<style>
.p-0 {
  padding: 0px;
}
.flex {
  display: flex;
}
.justify-space-between {
  justify-content: space-between;
}
.align-center {
  align-items: center;
}
.sns-login {
  width: 80px;
  margin-right: 8px;
}
.sns-login:hover {
  cursor:pointer;
}
.signin-head {
  margin: 40px auto;
}
.signin-input {
  text-align: left;
  margin-top: 12px;
}
.login-text {
  
}
.find-id-password {
  text-align: right;
  margin-bottom: 16px;
}
.login-btn {
  font-weight: 600;
  font-size: 16px;
  width: 412px;
  height: 48px;
  margin-top: 8px;
}
#signInForm div label {
  width: 44px;
  height: 20px;
  font-weight: 600;
  font-size: 16px;
  line-height: 20px;
  color: #000000;
}
#signInForm div input, select {
  box-sizing: border-box;
  width: 412px;
  height: 40px;
  background: #FFFFFF;
  border: 1px solid #C1BBBB;
  border-radius: 8px;
  padding: 10px;
}
#signInForm div {
  padding-top: 4px;
}
#signInForm span {
  font-size: 12px;
  color: #C699FF;
}
#signInForm span:hover {
  cursor: pointer;
  color:#8227fa;
  text-decoration: underline;
}
</style>