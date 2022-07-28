<template>
  <div>
    돌아오신 것을 환영해요!
  </div>
  <div>
    다시 만난다니 너무 반가워요!
  </div>
  <form action="POST" @submit.prevent>
    <div>
      <div><label for="signInId">아이디</label></div>
      <div><input type="text" name="signInId" id="signInId"></div>
    </div>
    <div>
      <div><label for="signInId">비밀번호</label></div>
      <div><input type="text" name="signInId" id="signInId"></div>
    </div>
    <div>
      아이디 / 로그인
    </div>
    <div>
      <img class="sns-login" src="@/assets/land/naver_login_icon.png" alt="naver_login_icon">
      <img class="sns-login" src="@/assets/land/google_login_icon.png" alt="google_login_icon">
      <img class="sns-login" src="@/assets/land/kakao_login_icon.png" alt="kakao_login_icon">
    </div>
    <div>
      계정이 필요한가요?
    </div>
    <button>로그인</button>
  </form>
</template>

<script>
export default {
  name: 'SignIn'
}
</script>

<style>
.sns-login {
  width: 60px;
}
</style>