<template>
  <div class="sign-head">
    계정 만들기
  </div>
  <Form id="signUpForm" action="POST" @submit="account.signup(credentials, birth)">
    <div>
      <div><label for="signUpId">아이디</label></div>
      <div>
        <Field type="text" name="signUpId" id="signUpId" placeholder="아이디를 입력해 주세요." v-model="credentials.id" :rules="validateId" />
      </div>
      <ErrorMessage class="error-message" name="signUpId"/>
    </div>
    <div>
      <div><label for="signUpPassword">비밀번호</label></div>
      <div><Field type="password" name="signUpPassword" id="signUpPassword" placeholder="8~16자의 영문, 숫자, 특수문자를 모두 조합해 입력해주세요." v-model="credentials.password" :rules="validatePassword" /></div>
      <ErrorMessage class="error-message" name="signUpPassword"/>
    </div>
    <div>
      <div><label for="signUpPasswordConfirm">비밀번호 확인</label></div>
      <div><Field type="password" name="signUpPasswordConfirm" id="signUpPasswordConfirm" placeholder="확인을 위하여 위와 동일하게 입력해주세요." :rules="validateRePassword" /></div>
      <ErrorMessage class="error-message" name="signUpPasswordConfirm"/>
    </div>
    <div>
      <div><label for="signUpName">이름</label></div>
      <div><Field type="text" name="signUpName" id="signUpName" placeholder="한글/영문으로 입력해주세요." v-model="credentials.name" :rules="validatename" /></div>
      <ErrorMessage class="error-message" name="signUpName"/>
    </div>
    <div>
      <div><label for="signUpNickName">닉네임</label></div>
      <div>
        <Field class="middle-input" type="text" name="signUpNickName" id="signUpNickName" placeholder="닉네임을 입력해주세요." v-model="credentials.nickname" :rules="validateNickname" />
        <button type="button">중복 확인</button>
      </div>
      <ErrorMessage class="error-message" name="signUpNickName"/>
    </div>
    <div>
      <div><label for="signUpYear">생년월일</label></div>
      <div>
        <Field class="sort-input" type="text" name="signUpYear" id="signUpYear" placeholder="년(4자)" v-model="birth.year" :rules="validateBirthYear"/>
        <Field class="sort-input" name="signUpMonth" id="signUpMonth" v-model="birth.month" as="select" :rules="validateBirthMonth">
          <option
             v-for="(month, idx) in months"
            :key="idx"
            :value="months_val[idx]"
          >{{ month }}</option>
        </Field>
        <Field class="sort-input" type="text" name="signUpDay" id="signUpDay" placeholder="일" style="margin:0;" v-model="birth.day" :rules="validateBirthDay"/>
      </div>
      <ErrorMessage v-if="if_birth===0" class="error-message" name="signUpYear"/>
      <ErrorMessage v-if="if_birth===1"  class="error-message" name="signUpMonth"/>
      <ErrorMessage v-if="if_birth===2"  class="error-message" name="signUpDay"/>
    </div>
    <div>
      <div><label for="signUpGender">성별</label></div>
      <div>
        <Field name="signUpGender" id="signUpGender" v-model="credentials.gender" as="select" :rules="isRequired">
          <option
            v-for="(gender, idx) in genders"
            :key="idx"
            :value="genders_val[idx]"
          >{{ gender }}</option>
        </Field>
      </div>
      <ErrorMessage class="error-message" name="signUpGender"/>
    </div>
    <div>
      <div><label for="signUpRegion">지역</label></div>
      <div>
        <Field name="signUpRegion" id="signUpRegion" v-model="credentials.address" as="select" :rules="isRequired">
          <option
            v-for="(region, idx) in regions"
            :key="idx"
            :value="regions_val[idx]"
          >{{ region }}</option>
        </Field>
      </div>
      <ErrorMessage class="error-message" name="signUpRegion"/>
    </div>
    <div>
      <div><label for="signUpSmoking">흡연여부</label></div>
      <div>
        <Field name="signUpSmoking" id="signUpSmoking" v-model="credentials.smoke" as="select" :rules="isRequired">
          <option
            v-for="(smoke, idx) in smokes"
            :key="idx"
            :value="smokes_val[idx]"
          >{{ smoke }}</option>
        </Field>
      </div>
      <ErrorMessage class="error-message" name="signUpSmoking"/>
    </div>
    <div>
      <div><label for="signUpPhoneNumber">휴대전화</label></div>
      <div>
        <Field class="middle-input" type="text" name="signUpPhoneNumber" id="signUpPhoneNumber" placeholder="-없이 입력해주세요." v-model="credentials.tel" :rules="validateTel" />
        <button type="button">전송하기</button>
      </div>
      <ErrorMessage class="error-message" name="signUpPhoneNumber"/>
    </div>
    <div>
      <div><label for="signUpCertificationNumber">인증번호</label></div>
      <div>
          <input class="middle-input" type="text" name="signUpCertificationNumber" id="signUpCertificationNumber"  placeholder="인증번호를 입력해주세요.">
          <button type="button">인증하기</button>
        </div>
    </div>
    <button type="submit" class="sign-btn">가입하기</button>
  </Form>
  <div class="go-login">
    이미 회원이신가요? <span @click="land.btnCh=1">로그인 하기</span>
  </div>
</template>

<script>
import { ref } from 'vue'
import { Field, Form, ErrorMessage } from 'vee-validate';
import { useAccountStore } from '@/stores/land/account'
import { useLandStore } from '@/stores/land/land'


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
    if_birth: 0,
  }
 },
 methods: {
    initCredentials() {
      this.credentials = {
        id: '',
        password: '',
        name: '',
        nickname: '',
        gender: '',
        birth: '',
        tel: '',
        smoke: '',
        address: '',
      }
    },
    isRequired(value) {
      if (!value) {
        return '필수정보 입니다.';
      }
      return true;
    },
    validateId(value) {
      if (!value) {
        return '필수정보 입니다.';
      }
      const idJ = /^[a-z]{1}[a-z0-9]{3,19}$/;
      if (!idJ.test(value)) {
        return '4~20자의 영문 소문자, 숫자만 사용 가능합니다.'
      }
      return true;
    },
    validateNickname(value) {
      if (!value) {
        return '필수정보 입니다.';
      }
      const NicknameJ = /^[가-힣a-zA-Z0-9]{2,16}$/
      if (!NicknameJ.test(value)) {
        return '한글과 영문 대 소문자를 사용하세요. (특수기호, 공백 사용 불가)';
      }
      return true;
    },
    validatename(value) {
      if (!value) {
        return '필수정보 입니다.';
      }
      const nameJ = /^[가-힣a-zA-Z]{1,}$/
      if (!nameJ.test(value)) {
        return '한글과 영문 대 소문자를 사용하세요. (특수기호, 공백 사용 불가)';
      }
      return true;
    },
    validatePassword(value) {
      if (!value) {
        return '필수정보 입니다.';
      }
      const pwJ = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$/;
      if (!pwJ.test(value)) {
        return '8~16자의 영문 대소문자, 숫자, 특수문자 중 3가지 이상 조합';
      }
      return true;
    },
    validateRePassword(value) {
      if (!value) {
        return '필수정보 입니다.';
      }
      if (this.credentials.password !==value) {
        return '비밀번호가 일치하지 않습니다.';
      }
      return true;
    },
    validateBirthYear(value) {
      if (!value) {
        this.if_birth = 0
        return '태어난 년도 4자리를 정확하게 입력하세요.';
      }
      const year = /^[1-2]{1}[0-9]{1}[0-9]{1}[0-9]{1}$/
      if (!year.test(value)) {
        this.if_birth = 0
        return '태어난 년도 4자리를 정확하게 입력하세요.';
      }
      this.if_birth = 1
      return true;
    },
    validateBirthMonth(value) {
      if (!value) {
        this.if_birth = 1
        return '태어난 월을 선택하세요.';
      }
      this.if_birth = 2
      return true;
    },
    validateBirthDay(value) {
      if (!value) {
        this.if_birth = 2
        return '태어난 일(날짜)를 정확하게 입력하세요';
      }
      const day = Number(value)
      if (!(day > 0 && day <= 31)) {
        this.if_birth = 2
        return '태어난 날짜를 정확하게 입력하세요.';
      }
      this.if_birth = 0
      return true;
    },
    validateTel(value) {
      if (!value) {
        return '필수정보 입니다.';
      }
      const phoneJ = /^01([0|1|6|7|8|9]?)?([0-9]{3,4})?([0-9]{4})$/;
      if (!phoneJ.test(value)) {
        return '전화번호가 올바르지 않습니다.';
      }
      return true;
    },
 },
 setup() {
  const account = useAccountStore()
  const land = useLandStore()
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
  return {
    account,
    credentials,
    birth,
    land,
  }
 }
}
</script>

<style>
.sign-head {
  width: 400px;
  height: 40px;
  font-weight: 700;
  font-size: 32px;
  line-height: 39px;
  color: #8227fa;
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
.error-message {
  font-size: 4px;
  color: red
}
.go-login {
  margin-top: 20px;
  font-size: 16px;
  text-align: center;
}
.go-login span {
  font-size: 12px;
  color: #C699FF;
}
.go-login span:hover {
  cursor: pointer;
  color:#8227fa;
  text-decoration: underline;
}
</style>