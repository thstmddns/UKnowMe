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
        id="naver_login_icon"
        class="sns-login"
        src="@/assets/land/naver_login_icon.png"
        alt="naver_login_icon"
        style="display: none"
      />
      <div id="naver_id_login"></div>
      <input
        @click="naverTokenSave()"
        type="text"
        id="aT"
        style="display: none"
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
  </div>
</template>

<script>
import { ref, onMounted } from "vue";
import { useAccountStore } from "@/stores/land/account";
import { useLandStore } from "@/stores/land/land";

const client_id = "5OSOWuXn6DTVQB4_h5Pc";
const callbackUrl = "http://localhost:8080/ntpu";

export default {
  name: "SignIn",
  methods: {
    naverTokenSave() {
      this.account.snsToken.naver = document.getElementById("aT").value;
      this.account.naverLogin();
    },
  },
  setup() {
    const account = useAccountStore();
    const land = useLandStore();
    const credentials = ref({
      id: "",
      password: "",
    });

    onMounted(() => {
      // naver
      const naver_id_login = new window.naver_id_login(client_id, callbackUrl);
      const state = naver_id_login.getUniqState();
      naver_id_login.setState(state);
      naver_id_login.setPopup();
      naver_id_login.init_naver_id_login();

      const naver_id_login_anchor = document.getElementById(
        "naver_id_login_anchor"
      );
      const naver_login_icon = document.getElementById("naver_login_icon");

      naver_id_login_anchor.firstChild.removeAttribute("width");
      naver_id_login_anchor.firstChild.removeAttribute("height");

      naver_id_login_anchor.firstChild.src = naver_login_icon.src;
      naver_id_login_anchor.firstChild.className += "sns-login";
      // !naver
      // google
      // !google
    });
    const aa = ref("");
    return {
      account,
      credentials,
      land,
      aa,
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