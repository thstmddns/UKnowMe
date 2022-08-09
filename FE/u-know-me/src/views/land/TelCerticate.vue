<template>
  <div class="tel">
    <div class="pass-header">
      <div>PASS</div>
    </div>
    <div class="tel-body1" v-if="btnCh===0">
      <hr style="margin-left:-14px;">
      <img src="@/assets/land/telimg.png" alt="alt">
      <div class="tel-body1-text">
        <p style="font-weight:600">알뜰폰이란?</p>
        <p>이동통신사(SKT, KT, LGU+)의 통신망을 임대하여 별도의</p>
        <p>휴대폰 서비스(MVNO)를 제공하는 사업자를 말합니다.</p>
      </div>
      <div class="tel-body1-check">
        <div>
          <div style="font-weight:600"><input type="checkbox" id="check1"><label for="check1"></label>본인확인을 하기 위한 필수사항에 전체 동의합니다.</div>
        </div>
        <div class="a-text">
          <div class="flex justify-between">
            <div><input type="checkbox" id="check2"><label for="check2"></label>개인정보이용동의</div>
            <div><input type="checkbox" id="check3"><label for="check3"></label>고유식별정보처리동의</div>
          </div>
          <div class="flex justify-between">
            <div><input type="checkbox" id="check4"><label for="check4"></label>서비스이용약관동의</div>
            <div style="margin-right:14px;"><input type="checkbox" id="check5"><label for="check5"></label>통신사이용약관동의</div>
          </div>
          <div>
            <div><input type="checkbox" id="check6"><label for="check6"></label>광고성 정소수신 동의(선택)</div>
          </div>
        </div>
        <div style="text-align:center;"><button class="tel-btn" @click="btnCh=1">시작하기</button></div>
      </div>
    </div>
    <div class="tel-body2" v-if="btnCh!==0">
      <div class="flex text-center tel-body2-header">
        <div @click="btnCh=1" class="tel-body2-header-text" :class="{ 'tel-text':btnCh===1}">QR인증</div>
        <div @click="btnCh=2" class="tel-body2-header-text" :class="{ 'tel-text':btnCh===2}">간편인증</div>
        <div @click="btnCh=3" class="tel-body2-header-text" :class="{ 'tel-text':btnCh===3}">문자인증</div>
      </div>
      <div style="margin-left:12px;">
        <div v-if="btnCh===1">
          <img class="pass-img" src="@/assets/land/pass_qr.png" alt="alt">
          <input class="c-num-input" type="text" v-model="number" disabled>
          <div class="tel-ok-btn">
            <button class="tel-cancel-btn" @click="closeBtn()">취소</button>
            <button class="tel-confirm-btn" @click="sendNum()">확인</button>
          </div>
        </div>
        <div v-if="btnCh===2">
          <img class="pass-img" src="@/assets/land/pass_easy.png" alt="alt">
          <div class="tel-ok-btn">
            <button class="tel-cancel-btn" @click="closeBtn()">취소</button>
            <button class="tel-confirm-btn" @click="sendNum()">확인</button>
          </div>
        </div>
        <div v-if="btnCh===3">
          <img class="pass-img" src="@/assets/land/pass_message.png" alt="alt">
          <div class="tel-ok-btn">
            <button class="tel-cancel-btn" @click="closeBtn()">취소</button>
            <button class="tel-confirm-btn" @click="sendNum()">확인</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'

export default {
  name: 'TelCerticate',
  methods: {
    sendNum() {
      window.close()
    },
    closeBtn() {
      window.close()
    }
  },
  setup() {
    const btnCh = ref(1)
    const number = ref(1234)
    return {
      btnCh,
      number
    }
  }
}
</script>

<style>
.c-num-input {
  position: absolute;
  width: 152px;
  height: 48px;
  border: 2px solid rgb(166, 106, 222);
  background: white;
  left: 224px;
  top: 400px;
  font-size: 40px;
}
.pass-img {
  width: 468px;
  height: 440px;
}
.tel {
  overflow-x: hidden;
  overflow-y: auto;
}
.justify-between {
  justify-content: space-between;
}
.pass-header {
  position: relative;
  font-weight: 700;
  font-size: 24px;
}
.pass-header div {
  margin: 8px 0 0 8px;
}
.tel-body1 hr {
  position: relative;
  background-color: gray;
  height: 2px;
  top: -8px;
}
.tel-body1 {
  margin-left: 14px;
}
.tel-body1 img {
  width: 96vw;
}
.tel-body1-text {
  margin-bottom: 20px;
}
.tel-body1-text p {
  margin: 0;
  font-size: 14px;
}
.tel-body1-check {
  margin-right: 14px;
}
.tel-body1-check label {
  margin-right: 10px;
}
.tel-body1-check input[type="checkbox"]{
  display: none;
}
.tel-body1-check input[type="checkbox"] + label{
  display: inline-block;
  width: 16px;
  height: 16px;
  background-color: rgba(128, 128, 128, 0.3);
  position: relative;
}
.tel-body1-check input[type="checkbox"]:checked + label::after{
  content:'✔';
  font-size: 12px;
  width: 16px;
  height: 16px;
  text-align: center;
  position: absolute;
  left: 0;
  top:0;
}
.tel-body1-check div {
  margin-top: 4px;
}
.a-text {
  text-decoration-line: underline;
  font-size: 14px;
  color: rgb(73, 73, 73);
}
.tel-btn {
  width: 95%;
  height: 48px;
  border: none;
  border-radius: 0;
  color: white;
  background-color: blueviolet;
  margin: 32px 0 16px -14px;
}
.tel-body2{
  margin-top: 4px;
}
.tel-body2-header {
  color: gray;
  cursor: pointer;
}
.tel-body2-header-text {
  width: 33%;
  height: 40px;
  line-height: 40px;
  border: 1px solid rgba(128, 128, 128, 0.2);
  background-color: rgba(199, 199, 199, 0.3);;
}
.tel-text {
  background-color: blueviolet;
  color: white;
}
.tel-ok-btn {
  margin-top: 20px;
  margin-left: 16px;
}
.tel-cancel-btn {
  width: 116px;
  height: 48px;
  border: none;
  border-radius: 0;
  color: white;
  background-color: rgba(128, 128, 128, 0.3);
  margin-right: 1vw;
}
.tel-confirm-btn {
  width: 312px;
  height: 48px;
  border: none;
  border-radius: 0;
  color: white;
  background-color: blueviolet;
}
</style>