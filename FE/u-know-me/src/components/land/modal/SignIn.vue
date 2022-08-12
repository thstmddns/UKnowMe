<template>
  <div class="sign-head signin-head">
    <div>너, 나 알아?</div>
    <div style="font-size: 16px">Welcome Back</div>
  </div>
  <form
    id="signInForm"
    action="POST"
    @submit.prevent="account.login(credentials)"
  >
    <div class="signin-input flex">
      <div class="icon-cell">
        <div class="icon-signin">
          <i class="fa-solid fa-user"></i>
        </div>
      </div>
      <div>
        <input
          type="text"
          name="signInId"
          id="signInId"
          placeholder="아이디"
          v-model="credentials.id"
        />
      </div>
    </div>
    <div class="signin-input flex">
      <div>
        <div class="icon-signin">
          <i class="fa-solid fa-unlock-keyhole"></i>
        </div>
      </div>
      <div>
        <input
          type="password"
          name="signInPassword"
          id="signInPassword"
          placeholder="비밀번호"
          v-model="credentials.password"
        />
      </div>
    </div>
    <div
      class="text-left"
      style="margin: 0; color: red; font-size: 12px"
      v-if="account.authError.login === 1"
    >
      아이디 또는 비밀번호를 잘못 입력했습니다.
      <br />입력하신 내용을 다시 확인해주세요.
    </div>
    <div class="find-id-password">
      <span @click="land.btnCh = 4">아이디/</span
      ><span @click="land.btnCh = 5">비밀번호 찾기</span>
    </div>
    <button class="login-btn">로그인</button>
  </form>
  <p class="login-signup">
    <span @click="land.btnCh = 2">계정이 필요하신가요?</span>
  </p>
  <div class="hr-sect">SNS 계정으로 로그인하기</div>
  <div class="flex justify-center align-center">
    <div>
      <img
        @click="account.naverLogin()"
        id="naver_login_icon"
        class="sns-login"
        src="@/assets/land/naver_login_icon.png"
        alt="naver_login_icon"
      />
    </div>
    <div>
      <img
        @click="account.kakaoLogin()"
        class="sns-login"
        src="@/assets/land/kakao_login_icon.png"
        alt="kakao_login_icon"
      />
    </div>
    <div id="snsLogin" @click="account.socialLogin()"></div>
  </div>
</template>

<script>
import { ref } from "vue";
import { useAccountStore } from "@/stores/land/account";
import { useLandStore } from "@/stores/land/land";

export default {
  name: "SignIn",
  setup() {
    const account = useAccountStore();
    const land = useLandStore();
    const credentials = ref({
      id: "",
      password: "",
    });
    return {
      account,
      credentials,
      land,
    };
  },
};
</script>

<style>
#naver_id_login {
  z-index: -1;
}
.flex {
  display: flex;
}
.justify-center {
  justify-content: center;
}
.align-center {
  align-items: center;
}
.sns-login {
  width: 188px;
  height: 48px;
  margin-right: 12px;
}
.sns-login:hover {
  cursor: pointer;
}
.signin-head {
  margin: 32px auto;
}
.signin-input {
  text-align: left;
  margin-top: 12px;
  border-top: 0;
  border-left: 0;
  border-right: 0;
  border-bottom: 1px solid #c1bbbb;
}
.signin-input:focus-within {
  border-bottom: 2px solid #c1bbbb;
}
.icon-signin {
  color: #c699ff;
  font-size: 20px;
  margin-left: 12px;
}
.find-id-password {
  text-align: right;
}
.login-btn {
  font-weight: 600;
  font-size: 16px;
  width: 412px;
  height: 48px;
  margin-top: 24px;
}
#signInForm div label {
  width: 44px;
  height: 20px;
  font-weight: 600;
  font-size: 16px;
  line-height: 20px;
  color: #000000;
}
#signInForm div input {
  box-sizing: border-box;
  width: 370px;
  height: 40px;
  border: 0;
  margin-left: 16px;
}
#signInForm div input:focus {
  outline: none;
}
#signInForm div {
  padding-top: 4px;
}
#signInForm span {
  font-size: 12px;
  color: #c699ff;
}
#signInForm span:hover {
  cursor: pointer;
  color: #8227fa;
  text-decoration: underline;
}
.login-signup {
  font-size: 16px;
  color: #c699ff;
  margin-bottom: 32px;
}
.login-signup span:hover {
  cursor: pointer;
  color: #8227fa;
  text-decoration: underline;
}
.hr-sect {
  display: flex;
  flex-basis: 100%;
  align-items: center;
  color: rgba(0, 0, 0, 0.35);
  font-size: 12px;
  margin: 16px 0px;
}
.hr-sect::before,
.hr-sect::after {
  content: "";
  flex-grow: 1;
  background: rgba(0, 0, 0, 0.35);
  height: 1px;
  font-size: 0px;
  line-height: 0px;
  margin: 0px 16px;
}
</style>