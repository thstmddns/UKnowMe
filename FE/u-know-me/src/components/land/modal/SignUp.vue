<template>
  <div class="sign-head">
    계정 만들기
  </div>
  <Form id="signUpForm" action="POST" @submit.prevent="account.signup(credentials, birth)">
    <div>
      <div><label for="signUpId">아이디</label></div>
      <div>
        <Field type="text" name="signUpId" id="signUpId" placeholder="아이디를 입력해 주세요." v-model="credentials.id" :rules="isRequired" />
      </div>
      <ErrorMessage name="signUpId"/>
    </div>
    <div>
      <div><label for="signUpPassword">비밀번호</label></div>
      <div><input type="password" name="signUpPassword" id="signUpPassword" placeholder="8~16자의 영문, 숫자, 특수문자를 모두 조합해 입력해주세요." v-model="credentials.password"></div>
    </div>
    <div>
      <div><label for="signUpPasswordConfirm">비밀번호 확인</label></div>
      <div><input type="password" name="signUpPasswordConfirm" id="signUpPasswordConfirm" placeholder="확인을 위하여 위와 동일하게 입력해주세요."></div>
    </div>
    <div>
      <div><label for="signUpName">이름</label></div>
      <div><input type="text" name="signUpName" id="signUpName" placeholder="한글/영문으로 입력해주세요." v-model="credentials.name"></div>
    </div>
    <div>
      <div><label for="signUpNickName">닉네임</label></div>
      <div>
        <input class="middle-input" type="text" name="signUpNickName" id="signUpNickName" placeholder="닉네임을 입력해주세요." v-model="credentials.nickname">
        <button type="button">중복 확인</button>
      </div>
    </div>
    <div>
      <div><label for="signUpYear">생년월일</label></div>
      <div>
        <input class="sort-input" type="text" name="signUpYear" id="signUpYear" placeholder="년(4자)" v-model="birth.year">
        <select class="sort-input" name="signUpMonth" id="signUpMonth" v-model="birth.month">
          <option
             v-for="(month, idx) in months"
            :key="idx"
            :value="months_val[idx]"
          >{{ month }}</option>
        </select>
        <input class="sort-input" type="text" name="signUpDay" id="signUpDay" placeholder="일" style="margin:0;" v-model="birth.day">
      </div>
    </div>
    <div>
      <div><label for="signUpGender">성별</label></div>
      <div>
        <select name="signUpGender" id="signUpGender" v-model="credentials.gender">
          <option
            v-for="(gender, idx) in genders"
            :key="idx"
            :value="genders_val[idx]"
          >{{ gender }}</option>
        </select>
      </div>
    </div>
    <div>
      <div><label for="signUpRegion">지역</label></div>
      <div>
        <select name="signUpRegion" id="signUpRegion" v-model="credentials.address">
          <option
            v-for="(region, idx) in regions"
            :key="idx"
            :value="regions_val[idx]"
          >{{ region }}</option>
        </select>
      </div>
    </div>
    <div>
      <div><label for="signUpSmoking">흡연여부</label></div>
      <div>
        <select name="signUpSmoking" id="signUpSmoking" v-model="credentials.smoke">
          <option
            v-for="(smoke, idx) in smokes"
            :key="idx"
            :value="smokes_val[idx]"
          >{{ smoke }}</option>
        </select>
      </div>
    </div>
    <div>
      <div><label for="signUpPhoneNumber">휴대폰 번호</label></div>
      <div>
        <input class="middle-input" type="text" name="signUpPhoneNumber" id="signUpPhoneNumber" placeholder="-없이 입력해주세요." v-model="credentials.tel">
        <button type="button">전송하기</button>
      </div>
    </div>
    <div>
      <div><label for="signUpCertificationNumber">인증번호</label></div>
      <div>
          <input class="middle-input" type="text" name="signUpCertificationNumber" id="signUpCertificationNumber"  placeholder="인증번호 입력(10분 이내)">
          <button type="button">인증하기</button>
        </div>
    </div>
    <button type="submit" class="sign-btn">가입하기</button>
  </Form>
</template>

<script>
import { ref } from 'vue'
import { Field, Form, ErrorMessage } from 'vee-validate';
import { useAccountStore } from '@/stores/land/account'

export default {
 name: "SignUp",
 components: {
  Field, 
  Form, 
  ErrorMessage,
 },
 data() {
  return {
    months: ['월', '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    months_val: ['', '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
    genders: ['성별', '남자', '여자'],
    genders_val: ['', 'M', 'F'],
    regions: ['지역', '서울특별시', '부산광역시', '대구광역시', '인천광역시', '대전광역시', '광주광역시', '울산광역시', '세종특별자치시', '강원도', '경기도', '충청남도', '충청북도', '전라북도', '전라남도', '경상북도', '경상남도', '제주특별자치도'],
    regions_val: ['', '서울특별시', '부산광역시', '대구광역시', '인천광역시', '대전광역시', '광주광역시', '울산광역시', '세종특별자치시', '강원도', '경기도', '충청남도', '충청북도', '전라북도', '전라남도', '경상북도', '경상남도', '제주특별자치도'],
    smokes: ['선택', '흡연', '비흡연'],
    smokes_val: ['', 'Y', 'N'],
  }
 },
 methods: {
    isRequired(value) {
      if (!value) {
        return '필수항목 입니다.';
      }
      return true;
    },
    validateId(value) {
      if (!value) {
        return 'this field is required';
      }
      
      return true;
    },
    validateNickname(value) {
      // if the field is empty
      if (!value) {
        return 'This field is required';
      }
      // if the field is not a valid email
      const regex = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i;
      if (!regex.test(value)) {
        return 'This field must be a valid email';
      }
      // All is good
      return true;
    },
    validatePassword(value) {
      if (!value) {
        return 'this field is required';
      }
      
      return true;
    },
    validateBirth(value) {
      if (!value) {
        return 'this field is required';
      }
      
      return true;
    },
    validateTel(value) {
      if (!value) {
        return 'this field is required';
      }
      
      return true;
    },
 },
 setup() {
  const account = useAccountStore()
  const birth = ref({
    year: '',
    month: '',
    day: '',
  })
  const credentials = ref({
    id: '',
    password: '',
    name: '',
    nickname: '',
    gender: '',
    birth: '',
    tel: '',
    smoke: '',
    address: '',
  })
  // https://www.nextree.co.kr/p4327/
  // //모든 공백 체크 정규식
	// const empJ = /\s/g;
	// //아이디 정규식
	// const idJ = /^[a-z0-9]{4,12}$/;
	// // 비밀번호 정규식
	// const pwJ = /^[A-Za-z0-9]{4,12}$/; 
	// // 이름 정규식
	// const nameJ = /^[가-힣]{2,6}$/;
	// // 이메일 검사 정규식
	// const mailJ = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	// // 휴대폰 번호 정규식
	// const phoneJ = /^01([0|1|6|7|8|9]?)?([0-9]{3,4})?([0-9]{4})$/;

  return {
    account,
    credentials,
    birth,
  }
 }
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
#signUpForm button:active {
  background: #8122fe;
}
</style>