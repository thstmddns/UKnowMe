<template>
  <div class="sign-head">
    계정 만들기
  </div>
  <form id="signUpForm" action="POST" @submit.prevent>
    <div>
      <div><label for="signUpId">아이디</label></div>
      <div><input type="text" name="signUpId" id="signUpId"></div>
    </div>
    <div>
      <div><label for="signUpNickName">닉네임</label></div>
      <div>
        <input class="middle-input" type="text" name="signUpNickName" id="signUpNickName">
        <button>중복 확인</button>
      </div>
    </div>
    <div>
      <div><label for="signUpPassword">비밀번호</label></div>
      <div><input type="password" name="signUpPassword" id="signUpPassword"></div>
    </div>
    <div>
      <div><label for="signUpPasswordConfirm">비밀번호 확인</label></div>
      <div><input type="password" name="signUpPasswordConfirm" id="signUpPasswordConfirm"></div>
    </div>
    <div>
      <div><label for="signUpName">이름</label></div>
      <div><input type="text" name="signUpName" id="signUpName"></div>
    </div>
    <div>
      <div><label for="signUpYear">생년월일</label></div>
      <div>
        <input class="sort-input" type="text" name="signUpYear" id="signUpYear" placeholder="년(4자)">
        <select class="sort-input" name="signUpMonth" id="signUpMonth">
          <option value="">월</option>
          <option value="1">1월</option>
          <option value="2">2월</option>
          <option value="3">3월</option>
          <option value="4">4월</option>
          <option value="5">5월</option>
          <option value="6">6월</option>
          <option value="7">7월</option>
          <option value="8">8월</option>
          <option value="9">9월</option>
          <option value="10">10월</option>
          <option value="11">11월</option>
          <option value="12">12월</option>
        </select>
        <input class="sort-input" type="text" name="signUpDay" id="signUpDay" placeholder="일" style="margin:0;">
      </div>
    </div>
    <div>
      <div><label for="signUpGender">성별</label></div>
      <div class="radio-input">
        <select name="signUpGender" id="signUpGender">
          <option value="">성별</option>
          <option value="man">남자</option>
          <option value="woman">여자</option>
        </select>
      </div>
    </div>
    <div>
      <div><label for="signUpSmoking">흡연여부</label></div>
      <div class="checkbox-input">
        <input type="checkbox" name="signUpSmoking" id="signUpSmoking">
      </div>
    </div>
    <div>
      <div><label for="signUpPhoneNumber">휴대폰 번호</label></div>
      <div>
        <input class="middle-input" type="text" name="signUpPhoneNumber" id="signUpPhoneNumber">
        <button>인증</button>
      </div>
    </div>
    <div>
      <div><label for="signUpCertificationNumber">인증번호</label></div>
      <div><input type="text" name="signUpCertificationNumber" id="signUpCertificationNumber"></div>
    </div>
    <button class="sign-btn">가입하기</button>
  </form>
</template>

<script>
export default {
 name: "SignUp",
}
</script>

<style>
.sign-head {
  width: 300px;
  height: 40px;
  font-weight: 700;
  font-size: 32px;
  line-height: 39px;
  color: #A056FF;
  padding-bottom: 40px;
}
#signUpForm {
  height: 84%;
  margin-right: -32px;
  overflow-x: hidden;
  overflow-y: auto;
}
#signUpForm::-webkit-scrollbar {
  width: 10px;
}
#signUpForm::-webkit-scrollbar-thumb {
  height: 30%;
  background: #A056FF;
  border-radius: 10px;
}
#signUpForm::-webkit-scrollbar-track {
  background: rgba(160, 86, 255, .1);
}
#signUpForm div {
  padding-top: 4px;
}
#signUpForm div label {
  width: 44px;
  height: 20px;
  font-weight: 600;
  font-size: 16px;
  line-height: 20px;
  color: #000000;
}
#signUpForm div input, select {
  box-sizing: border-box;
  width: 412px;
  height: 40px;
  background: #FFFFFF;
  border: 1px solid #C1BBBB;
  border-radius: 8px;
  padding: 10px;
}
#signUpForm div .middle-input {
  width: 296px;
}
#signUpForm div .sort-input {
  width: 132px;
  margin-right: 8px;
}
#signUpForm div .radio-input input {
  width: 20px;
}
#signUpForm div .checkbox-input input {
  width: 20px;
}
#signUpForm button {
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
#signUpForm div button {
  width: 104px;
}
#signUpForm .sign-btn {
    width: 412px;
    margin: 32px 0px 4px 0px;
}
#signUpForm button:hover {
  background: #8d39fc;
}
</style>