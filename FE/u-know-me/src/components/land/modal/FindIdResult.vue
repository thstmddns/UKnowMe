<template>
  <div class="sign-head signin-head">
    <i class="fa-solid fa-id-card"></i>&#160;아이디 찾기
  </div>
  <div class="t-center">
    <div>
      아이디 찾기가 완료되었습니다.
    </div>
    <div class="find-result">
      ID는 <span>봉구스밥버거{{ account.findUserId }}</span> 입니다.
    </div>
    <div class="result-btn">
      <div class="flex justify-around">
        <button @click="land.btnCh=1">로그인</button>
        <button @click="land.btnCh=5">비밀번호 찾기</button>
      </div>
    </div>
  </div>
</template>

<script>
import { useAccountStore } from '@/stores/land/account'
import { useLandStore } from '@/stores/land/land'

export default {
  name:'FindIdResult',
  setup() {
    const account = useAccountStore()
    const land = useLandStore()
    return {
      account,
      land,
    }
  }
}
</script>

<style>
.justify-around {
  justify-content: space-around;
}
.find-result {
  margin: 40px 0;
}
.find-result span {
  color: #8227fa;
}
.result-btn {
  margin: 16px 0;
}
.result-btn button {
  width: 160px;
}
.t-center {
  text-align: center;
}
</style>