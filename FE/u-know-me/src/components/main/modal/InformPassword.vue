<template>
  <div class="sign-head text-center">
    <div>비밀번호 확인</div>
  </div>
  <div class="text-center imform-password-text">
    개인 정보 조회를 위해서는 인증이 필요합니다. <br>
    비밀번호를 입력 후 확인 버튼을 클릭해 주세요.
  </div>
  <form id="informPasswordForm" action="POST" @submit.prevent="[account.certificatePassword(password), password='']">
    <div class="inform-input">
      <div><label for="informPassword">비밀번호</label></div>
      <div>
        <input type="password" name="informPassword" id="informPassword" placeholder="비밀번호를 입력해 주세요." v-model="password">
      </div>
    </div>
    <div class="text-center"><button class="inform-btn">확인</button></div>
  </form>
</template>

<script>
import { ref } from 'vue'
import { useMainStore } from "@/stores/main/main";
import { useAccountStore } from "@/stores/land/account";

export default {
  name: "LogoutModal",
  components: {},
  setup() {
    const main = useMainStore();
    const account = useAccountStore();
    const password = ref('')
    return {
      main,
      account,
      password,
    };
  },
};
</script>

<style>
.imform-password-text {
  font-size: 16px;
  font-weight: 500;
  line-height:32px;
  margin: 16px 0;
}
.text-center {
  text-align: center;
}
#informPasswordForm div {
  padding-top: 4px;
}
#informPasswordForm div label {
  width: 44px;
  height: 20px;
  font-weight: 600;
  font-size: 16px;
  line-height: 20px;
  color: #000000;
}
#informPasswordForm div input, #informPasswordForm div select {
  box-sizing: border-box;
  width: 412px;
  height: 40px;
  background: #FFFFFF;
  border: 1px solid #C1BBBB;
  border-radius: 8px;
  padding: 10px;
}
.inform-input {
  margin: 32px 0;
}
.inform-btn {
  margin: 8px 0;
  width: 336px;
}
</style>