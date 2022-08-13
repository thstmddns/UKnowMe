<template>
  <div id="modifyInform">
    <div>
      <div class="inform-secure-head">비밀번호 변경</div>
      <hr>
    </div>
    <Form id="informSecureForm" action="POST" class="text-center" @submit="account.chagePassword(password)">
      <div>
        <div>
          <div class="text-inform"><label for="informSecurePassword">현재 비밀번호</label></div>
          <div>
            <div><Field type="password" name="informSecurePassword" id="informSecurePassword" placeholder="비밀번호를 입력해주세요.." :rules="validateCurrentPassword" /></div>
            <div class="text-left"><ErrorMessage class="error-message" name="informSecurePassword"/></div>
          </div>
        </div>
        <div>
          <div class="text-inform"><label for="informSecureNewPassword">새 비밀번호</label></div>
          <div>
            <div><Field type="password" name="informSecureNewPassword" id="informSecureNewPassword" placeholder="비밀번호를 입력해주세요.." v-model="password.changePassword" :rules="validatePassword" /></div>
            <div class="text-left"><ErrorMessage class="error-message" name="informSecureNewPassword"/></div>
          </div>
        </div>
        <div>
          <div class="text-inform"><label for="informSecureNewConfigPassword">새 비밀번호 확인</label></div>
          <div>
            <div><Field type="password" name="informSecureNewConfigPassword" id="informSecureNewConfigPassword" placeholder="비밀번호를 재입력해주세요." :rules="validateRePassword" /></div>
            <div class="text-left"><ErrorMessage class="error-message" name="informSecureNewConfigPassword"/></div>
          </div>
        </div>
      </div>
      <div><button type="submit" class="inform-secure-btn">비밀번호 변경하기</button></div>
    </Form>
    <div>
      <div class="inform-secure-head">회원탈퇴</div>
      <hr>
    </div>
    <div class="text-center">
      <button @click="account.deleteAccount()" class="inform-secure-btn">회원탈퇴</button>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import { Field, Form, ErrorMessage } from 'vee-validate';
import { useMainStore } from "@/stores/main/main";
import { useAccountStore } from "@/stores/land/account";

export default {
  name: "InformSecure",
  components: {
    Field, 
    Form, 
    ErrorMessage,
  },
  methods: {
    validateCurrentPassword(value) {
      if (!value) {
        return '필수정보 입니다.';
      }
      this.account.modifyCertificatePassword(value)
      if (!this.account.correctPassword) {
        return '현재 비밀번호와 일치하지 않습니다.'
      }
      return true;
    },
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
      if (this.password.changePassword !== value) {
        return '비밀번호가 일치하지 않습니다.';
      }
      return true;
    },
  },
  setup() {
    const main = useMainStore();
    const account = useAccountStore();
    const password = ref({
      id: account.currentUser.id,
      changePassword: '',
    })
    return {
      main,
      account,
      password,
    };
  },
};
</script>

<style>
#modifyInform {
  height: calc(100% - 100px);
  padding-right: 28px;
  margin-right: -38px;
  overflow-x: hidden;
  overflow-y: auto;
}
#modifyInform::-webkit-scrollbar {
  width: 10px;
}
#modifyInform::-webkit-scrollbar-thumb {
  height: 30%;
  background: #A056FF;
  border-radius: 10px;
}
#modifyInform::-webkit-scrollbar-track {
  background: rgba(160, 86, 255, .1);
}
.inform-secure-head {
  font-weight: 700;
  font-size: 24px;
}
.text-left {
  text-align: left;
  margin-left: 44px;
}
.text-inform {
  text-align: left;
  margin-left: 44px;
  margin-top: 4px;
}
.inform-secure-btn {
  margin: 32px 0;
  width: 412px;
}
#informSecureForm div {
  padding-top: 4px;
}
#informSecureForm div label {
  width: 44px;
  height: 20px;
  font-weight: 600;
  font-size: 16px;
  line-height: 20px;
  color: #000000;
}
#informSecureForm div input, #informSecureForm div select {
  box-sizing: border-box;
  width: 412px;
  height: 40px;
  background: #FFFFFF;
  border: 1px solid #C1BBBB;
  border-radius: 8px;
  padding: 10px;
}
</style>