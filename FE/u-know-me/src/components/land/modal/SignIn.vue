<template>
  <div class="sign-head signin-head">
    <div>너, 나 알아?</div>
    <div style="font-size:16px;">Welcome Back</div>
  </div>
  <form id="signInForm" action="POST" @submit.prevent="account.login(credentials)">
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
      <span @click="land.btnCh=4">아이디/</span><span @click="land.btnCh=5">비밀번호 찾기</span>
    </div>
    <button class="login-btn">로그인</button>
  </form>
  <p class="login-signup">
    <span @click="land.btnCh=2">계정이 필요하신가요?</span>
  </p>
  <div class="hr-sect">
    SNS 계정으로 로그인하기
  </div>
  <div class="flex justify-center align-center">
    <div>
      <img id="naver_login_icon" class="sns-login" src="@/assets/land/naver_login_icon.png" alt="naver_login_icon" style="display:none;">
      <div id="naver_id_login"></div>
    </div>
    <img @click="account.kakaoLogin()" class="sns-login" src="@/assets/land/kakao_login_icon.png" alt="kakao_login_icon">
    <section class="test">
      <div @click="GoogleLoginBtn">
        <img class="sns-login" src="@/assets/land/google_login_icon.png" alt="google_login_icon">
      </div>
      <div id="my-signin2" style="display:none;"></div>
    </section>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useAccountStore } from '@/stores/land/account'
import { useLandStore } from '@/stores/land/land'

const client_id = "5OSOWuXn6DTVQB4_h5Pc" 
const callbackUrl = "http://localhost:8080/test"

export default {
  name: 'SignIn',
  methods: {
    // openFindPage(pageNum) {
    //   let popUpName = '팝팝파파팝'
    //   if (pageNum === 0) {
    //     this.land.popBtnCh=0
    //     popUpName = '아이디 찾기'
    //   } else if (pageNum === 1) {
    //     this.land.popBtnCh=1
    //     popUpName = '비밀번호 찾기'
    //   }
    //   window.open(`http://localhost:8080/find?idp=${this.land.popBtnCh}`, popUpName, this.getLoginPopupFeatures());
    // },
    // getLoginPopupFeatures() {
    // var popupWidth = 480;
    // var popupHeight = 450;
    // var sLeft = window.screenLeft ? window.screenLeft : window.screenX ? window.screenX : 0;
    // var sTop = window.screenTop ? window.screenTop : window.screenY ? window.screenY : 0;
    // var popupLeft = screen.width / 2 - popupWidth / 2 + sLeft;
    // var popupTop = screen.height / 2 - popupHeight / 2 + sTop;
    // return ["width=".concat(popupWidth), "height=".concat(popupHeight), "left=".concat(popupLeft), "top=".concat(popupTop), 'scrollbars=yes', 'resizable=1'].join(',');
    // },
    GoogleLoginBtn() {
      var self = this;

      window.gapi.signin2.render('my-signin2', {
        scope: 'profile email',
        width: 240,
        height: 50,
        longtitle: true,
        theme: 'dark',
        onsuccess: this.GoogleLoginSuccess,
        onfailure: this.GoogleLoginFailure,
      });
      setTimeout(function () {
        if (!self.googleLoginCheck) {
          const auth = window.gapi.auth2.getAuthInstance();
          auth.isSignedIn.get();
          document.querySelector('.abcRioButton').click();
        }
      }, 1500)

    },
    async GoogleLoginSuccess(googleUser) {
      const profile = googleUser.getBasicProfile();
      console.log("ID: " + profile.getId()); // Don't send this directly to your server!
      console.log('Full Name: ' + profile.getName());
      console.log('Given Name: ' + profile.getGivenName());
      console.log('Family Name: ' + profile.getFamilyName());
      console.log("Image URL: " + profile.getImageUrl());
      console.log("Email: " + profile.getEmail());

      // The ID token you need to pass to your backend:
      const id_token = googleUser.getAuthResponse().id_token;
      console.log("ID Token: " + id_token);
    },
    //구글 로그인 콜백함수 (실패)
    GoogleLoginFailure(error) {
      console.log('삐용삐용 에러입니다.');
      console.log(error);
    },
  },
  setup() {
    const account = useAccountStore()
    const land = useLandStore()
    const credentials = ref({
      id: account.findUserId,
      password: '',
    })

    onMounted(() => {
      // naver
      const naver_id_login = new window.naver_id_login(client_id, callbackUrl);
      const state = naver_id_login.getUniqState();
      naver_id_login.setState(state);
      naver_id_login.setPopup();
      naver_id_login.init_naver_id_login();
      
      const naver_id_login_anchor = document.getElementById('naver_id_login_anchor')
      const naver_login_icon = document.getElementById('naver_login_icon')

      naver_id_login_anchor.firstChild.removeAttribute('width')
      naver_id_login_anchor.firstChild.removeAttribute('height')

      naver_id_login_anchor.firstChild.src = naver_login_icon.src
      naver_id_login_anchor.firstChild.className += 'sns-login'
      // !naver
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
  /* width: 80px; */
  height: 48px;
  margin-right: 12px;
}
.sns-login:hover {
  cursor:pointer;
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
  color: #C699FF;
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
  color: #C699FF;
}
#signInForm span:hover {
  cursor: pointer;
  color:#8227fa;
  text-decoration: underline;
}
.login-signup {
  font-size: 16px;
  color: #C699FF;
  margin-bottom: 32px;
}
.login-signup span:hover {
  cursor: pointer;
  color:#8227fa;
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
.hr-sect::before, .hr-sect::after {
  content: "";
  flex-grow: 1;
  background: rgba(0, 0, 0, 0.35);
  height: 1px;
  font-size: 0px;
  line-height: 0px;
  margin: 0px 16px;
}
</style>