<template>
  <div class="sign-head signin-head">
    <i class="fa-solid fa-id-card"></i>&#160;아이디 찾기
  </div>
  <form id="findIdForm" action="POST" @submit.prevent="account.findId(credentials)">
    <div>
      <div><label for="findIdName">이름</label></div>
      <div><input type="text" name="findIdName" id="findIdName" placeholder="이름을 입력해주세요." v-model="credentials.name"/></div>
    </div>
    <div class="find-input">
      <div><label for="findIdTel">휴대폰 번호</label></div>
      <div><input type="password" name="findIdTel" id="findIdTel" placeholder="-없이 입력해주세요." v-model="credentials.tel"/></div>
    </div>
    <button type="submit" class="sign-btn">아이디 찾기</button>
  </form>
  <div class="go-login">
    <span @click="land.btnCh=6">아이디 찾았으</span> | 
    <span @click="land.btnCh=1">로그인 창으로 돌아가기</span>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useAccountStore } from '@/stores/land/account'
import { useLandStore } from '@/stores/land/land'

export default {
  name:'FindId',
  setup() {
    const account = useAccountStore()
    const land = useLandStore()
    const credentials = ref({
      name: '',
      tel: '',
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
#findIdForm {
  height: calc(100% - 120px);
  margin-right: -32px;
  overflow-x: hidden;
  overflow-y: auto;
}
#findIdForm::-webkit-scrollbar {
  width: 10px;
}
#findIdForm::-webkit-scrollbar-thumb {
  height: 30%;
  background: #A056FF;
  border-radius: 10px;
}
#findIdForm::-webkit-scrollbar-track {
  background: rgba(160, 86, 255, .1);
}
#findIdForm div {
  padding-top: 4px;
}
#findIdForm div label {
  width: 44px;
  height: 20px;
  font-weight: 600;
  font-size: 16px;
  line-height: 20px;
  color: #000000;
}
#findIdForm div input, select {
  box-sizing: border-box;
  width: 412px;
  height: 40px;
  background: #FFFFFF;
  border: 1px solid #C1BBBB;
  border-radius: 8px;
  padding: 10px;
}
#findIdForm div .middle-input {
  width: 296px;
}
#findIdForm div .sort-input {
  width: 132px;
  margin-right: 8px;
}
#findIdForm button {
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
#findIdForm div button {
  width: 104px;
}
#findIdForm .sign-btn {
    width: 412px;
    margin: 32px 0px 4px 0px;
}
#findIdForm button:hover {
  background: #8d39fc;
}
#findIdForm button:active {
  background: #8122fe;
}
.find-input {
  margin: 16px 0;
}
</style>