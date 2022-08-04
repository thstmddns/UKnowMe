<template>
  <div class="sign-head signin-head">
    <div>SNS ID LOG IN</div>
  </div>
  <div class="sns-login-alert">
    <hr>
    최초 1회 로그인하시면, 자동으로 ㅇㅇ 아이디와 연결하여 편리하게 로그인 하실 수 있습니다.
    <hr>
  </div>
  <form id="signInSnsForm" action="POST" @submit.prevent="account.socialLogin('naver', credentials)">
    <div class="signin-input flex">
      <div class="icon-cell">
        <div class="icon-signin">
          <i class="fa-solid fa-user"></i>
        </div>
      </div>
      <div>
        <input type="text" name="signInId" id="signInId" placeholder="아이디" v-model="credentials.id"/>
      </div>
    </div>
    <div class="signin-input flex">
      <div>
        <div class="icon-signin">
          <i class="fa-solid fa-unlock-keyhole"></i>
        </div>
      </div>
      <div>
        <input type="password" name="signInPassword" id="signInPassword" placeholder="비밀번호" v-model="credentials.password"/>
      </div>
    </div>
    <div class="find-id-password">
      <span>아이디/</span><span>비밀번호 찾기</span>
    </div>
    <button class="login-btn">로그인</button>
  </form>
  <p class="login-signup">
      <span @click="land.btnCh=1">로그인 페이지로 돌아가기</span>
    </p>
</template>

<script>
import { ref } from 'vue'
import { useAccountStore } from '@/stores/land/account'
import { useLandStore } from '@/stores/land/land'

export default {
  name: 'SnsLogin',
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
.sns-login-alert {
  margin: 40px 0px;
  font-size: 16px;
  font-weight: 700;
}
#signInSnsForm div label {
  width: 44px;
  height: 20px;
  font-weight: 600;
  font-size: 16px;
  line-height: 20px;
  color: #000000;
}
#signInSnsForm div input {
  box-sizing: border-box;
  width: 370px;
  height: 40px;
  border: 0;
  margin-left: 16px;
}
#signInSnsForm div input:focus {
  outline: none;
}
#signInSnsForm div {
  padding-top: 4px;
}
#signInSnsForm span {
  font-size: 12px;
  color: #C699FF;
}
#signInSnsForm span:hover {
  cursor: pointer;
  color:#8227fa;
  text-decoration: underline;
}
</style>