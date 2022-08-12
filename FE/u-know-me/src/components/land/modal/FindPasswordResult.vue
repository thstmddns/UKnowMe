<template>
  <div class="sign-head">
    <i class="fa-solid fa-lock-open"></i>&#160;비밀번호 찾기
  </div>
  <div class="t-center">
    <div class="find-result">
      <span>{{ account.findUserId }}</span>님의 비밀번호를 재설정 해주세요.
    </div>
    <Form id="findPasswordResultForm" action="POST" class="text-center" @submit="account.chagePassword(password)">
      <div>
        <div class="find-password-margin">
          <div class="text-find-password"><label for="findPasswordNewPassword">새 비밀번호</label></div>
          <div>
            <div><Field type="password" name="findPasswordNewPassword" id="findPasswordNewPassword" placeholder="비밀번호를 입력해주세요.." v-model="password.changePassword" :rules="validatePassword" /></div>
            <div class="text-find-password"><ErrorMessage class="error-message" name="findPasswordNewPassword"/></div>
          </div>
        </div>
        <div class="find-password-margin">
          <div class="text-find-password"><label for="findPasswordNewConfigPassword">새 비밀번호 확인</label></div>
          <div>
            <div><Field type="password" name="findPasswordNewConfigPassword" id="findPasswordNewConfigPassword" placeholder="비밀번호를 재입력해주세요." :rules="validateRePassword" /></div>
            <div class="text-find-password"><ErrorMessage class="error-message" name="findPasswordNewConfigPassword"/></div>
          </div>
        </div>
      </div>
      <div><button type="submit" class="inform-secure-btn">비밀번호 변경하기</button></div>
    </Form>
  </div>
</template>

<script>
import { ref } from 'vue'
import { Field, Form, ErrorMessage } from 'vee-validate';
import { useAccountStore } from '@/stores/land/account'
import { useLandStore } from '@/stores/land/land'

export default {
  name:'FindIdResult',
  components: {
    Field, 
    Form, 
    ErrorMessage,
  },
  methods: {
    validatePassword(value) {
      if (!value) {
        return '필수정보 입니다.';
      }
      const pwJ = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$/;
      if (!pwJ.test(value)) {
        return '8~16자의 영문 대소문자, 숫자, 특수문자 중 3가지 이상 조합해야 합니다.';
      }
      return true;
    },
    validateRePassword(value) {
      if (!value) {
        return '필수정보 입니다.';
      }
      if (this.password.changePassword !==value) {
        return '비밀번호가 일치하지 않습니다.';
      }
      return true;
    },
  },
  setup() {
    const account = useAccountStore()
    const land = useLandStore()
    const password = ref({
      id: account.findUserId,
      changePassword: '',
    })
    return {
      account,
      land,
      password
    }
  }
}
</script>

<style>
.find-password-margin {
  margin-bottom: 16px;
}
.text-find-password {
  text-align: left;
  margin-left: 12px;
}
.justify-around {
  justify-content: space-around;
}
.find-result {
  margin: 32px 0;
  font-size: 16px;
}
.find-result span {
  color: #8227fa;
  font-size: 20px;
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
#findPasswordResultForm div label {
  width: 44px;
  height: 20px;
  font-weight: 600;
  font-size: 16px;
  line-height: 20px;
  color: #000000;
}
#findPasswordResultForm div input {
  box-sizing: border-box;
  width: 412px;
  height: 40px;
  background: #FFFFFF;
  border: 1px solid #C1BBBB;
  border-radius: 8px;
  padding: 10px;
}
</style>