<template>
  <div class="sign-head signin-head">
    <i class="fa-solid fa-lock-open"></i>&#160;비밀번호 찾기
  </div>
  <form id="findPasswordForm" action="POST" @submit.prevent="account.findPassword(credentials)">
    <div>
      <div><label for="findPasswordId">아이디</label></div>
      <div><input type="text" name="findPasswordId" id="findPasswordId" placeholder="아이디를 입력해주세요." v-model="credentials.id"/></div>
    </div>
    <div>
      <div><label for="findPasswordName">이름</label></div>
      <div><input type="text" name="findPasswordName" id="findPasswordName" placeholder="이름을 입력해주세요." v-model="credentials.name"/></div>
    </div>
    <div>
      <div><label for="findPasswordNumber">휴대전화</label></div>
      <div>
        <input class="middle-input" type="text" name="findPasswordNumber" id="findPasswordNumber" placeholder="-없이 입력해주세요." v-model="credentials.tel"/>
        <button @click="account.sendNumTel(credentials.tel)" type="button">전송하기</button>
      </div>
    </div>
    <div>
      <div><label for="findPasswordCertificationNumber">인증번호</label></div>
      <div>
        <input type="text" :class="{ 'disabled-input-bg': !account.sendTel }" name="findPasswordCertificationNumber" id="findPasswordCertificationNumber"  placeholder="인증번호를 입력해주세요." v-model="telCerticate" :disabled="!account.sendTel" />
        <input @click="telClick()" type="text" id="tel-input" style="display:none;">
      </div>
      <div class="error-div" v-if="!account.checkFind.tel"></div>
      <p v-if="account.checkFind.tel" class="correct-message">인증이 성공했습니다.</p>
    </div>
    <button type="submit" class="sign-btn">비밀번호 찾기</button>
  </form>
  <div class="go-login">
    <span @click="land.btnCh=4">아이디찾기 </span> &#160; 
    <span @click="land.btnCh=1">로그인 창으로 돌아가기</span>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useAccountStore } from '@/stores/land/account'
import { useLandStore } from '@/stores/land/land'

export default {
  name:'FindPassword',
  methods: {
    telClick() {
      this.telCerticate = document.getElementById('tel-input').value
    },
  },
  setup() {
    const account = useAccountStore()
    const land = useLandStore()
    const credentials = ref({
      id: '',
      name: '',
      tel: '',
    })
    const telCerticate = ref('')
    return {
      account,
      credentials,
      land,
      telCerticate,
    }
  }
}
</script>

<style>
#findPasswordForm {
  height: calc(100% - 120px);
  margin-right: -32px;
  overflow-x: hidden;
  overflow-y: auto;
}
#findPasswordForm::-webkit-scrollbar {
  width: 10px;
}
#findPasswordForm::-webkit-scrollbar-thumb {
  height: 30%;
  background: #A056FF;
  border-radius: 10px;
}
#findPasswordForm::-webkit-scrollbar-track {
  background: rgba(160, 86, 255, .1);
}
#findPasswordForm div {
  padding-top: 4px;
}
#findPasswordForm div label {
  width: 44px;
  height: 20px;
  font-weight: 600;
  font-size: 16px;
  line-height: 20px;
  color: #000000;
}
#findPasswordForm div input, select {
  box-sizing: border-box;
  width: 412px;
  height: 40px;
  background: #FFFFFF;
  border: 1px solid #C1BBBB;
  border-radius: 8px;
  padding: 10px;
}
#findPasswordForm div .middle-input {
  width: 296px;
}
#findPasswordForm div .sort-input {
  width: 132px;
  margin-right: 8px;
}
#findPasswordForm button {
  box-sizing: border-box;
  height: 40px;
  background: #A056FF;
  border: 1px solid #C1BBBB;
  font-weight: 400;
  font-size: 12px;
  line-height: 20px;
  text-align: center;
  color: #FFFFFF;
  margin-left: 8px;
}
#findPasswordForm div button {
  width: 104px;
}
#findPasswordForm .sign-btn {
    width: 412px;
    margin: 32px 0px 4px 0px;
}
#findPasswordForm button:hover {
  background: #8d39fc;
}
#findPasswordForm button:active {
  background: #8122fe;
}
#findPasswordForm .disabled-input-bg {
  background-color: #efefef;
}
.mb-16px {
  margin-bottom: 16px;
}
</style>