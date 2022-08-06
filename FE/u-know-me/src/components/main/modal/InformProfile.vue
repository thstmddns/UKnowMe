<template>
  <Form id="informModifyForm" action="POST" @submit="account.modifyInform(credentials)">
    <div>
      <div><label for="informModifyId">아이디</label></div>
      <div>
        <Field type="text" name="informModifyId" id="informModifyId" placeholder="아이디를 입력해 주세요." v-model="credentials.id" :rules="validateId" class="disabled-input-bg" disabled />
      </div>
      <ErrorMessage class="error-message" name="informModifyId"/>
    </div>
    <div>
      <div><label for="informModifyName">이름</label></div>
      <div><Field type="text" name="informModifyName" id="informModifyName" placeholder="한글/영문으로 입력해주세요." v-model="credentials.name" :rules="validatename" class="disabled-input-bg" disabled /></div>
      <ErrorMessage class="error-message" name="informModifyName"/>
    </div>
    <div>
      <div><label for="informModifyNickName">닉네임</label></div>
      <div>
        <Field class="middle-input" type="text" name="informModifyNickName" id="informModifyNickName" placeholder="닉네임을 입력해주세요." v-model="credentials.nickname" :rules="validateNickname" />
        <button type="button">중복 확인</button>
      </div>
      <ErrorMessage class="error-message" name="informModifyNickName"/>
    </div>
    <div>
      <div><label for="informModifyYear">생년월일</label></div>
      <div>
        <Field class="sort-input" type="text" name="informModifyYear" id="informModifyYear" placeholder="년(4자)" v-model="birth.year" :rules="validateBirthYear"/>
        <Field class="sort-input" name="informModifyMonth" id="informModifyMonth" v-model="birth.month" as="select" :rules="validateBirthMonth">
          <option
             v-for="(month, idx) in months"
            :key="idx"
            :value="months_val[idx]"
          >{{ month }}</option>
        </Field>
        <Field class="sort-input" type="text" name="informModifyDay" id="informModifyDay" placeholder="일" style="margin:0;" v-model="birth.day" :rules="validateBirthDay"/>
      </div>
      <ErrorMessage v-if="if_birth===0" class="error-message" name="informModifyYear"/>
      <ErrorMessage v-if="if_birth===1"  class="error-message" name="informModifyMonth"/>
      <ErrorMessage v-if="if_birth===2"  class="error-message" name="informModifyDay"/>
    </div>
    <div>
      <div><label for="informModifyGender">성별</label></div>
      <div>
        <Field name="informModifyGender" id="informModifyGender" v-model="credentials.gender" as="select" :rules="isRequired" class="disabled-input-bg" disabled >
          <option
            v-for="(gender, idx) in genders"
            :key="idx"
            :value="genders_val[idx]"
          >{{ gender }}</option>
        </Field>
      </div>
      <ErrorMessage class="error-message" name="informModifyGender"/>
    </div>
    <div>
      <div><label for="informModifyRegion">지역</label></div>
      <div>
        <Field name="informModifyRegion" id="informModifyRegion" v-model="credentials.address" as="select" :rules="isRequired">
          <option
            v-for="(region, idx) in regions"
            :key="idx"
            :value="regions_val[idx]"
          >{{ region }}</option>
        </Field>
      </div>
      <ErrorMessage class="error-message" name="informModifyRegion"/>
    </div>
    <div>
      <div><label for="informModifySmoking">흡연여부</label></div>
      <div>
        <Field name="informModifySmoking" id="informModifySmoking" v-model="credentials.smoke" as="select" :rules="isRequired">
          <option
            v-for="(smoke, idx) in smokes"
            :key="idx"
            :value="smokes_val[idx]"
          >{{ smoke }}</option>
        </Field>
      </div>
      <ErrorMessage class="error-message" name="informModifySmoking"/>
    </div>
    <div>
      <div><label for="informModifyPhoneNumber">휴대전화</label></div>
      <div>
        <Field type="text" name="informModifyPhoneNumber" id="informModifyPhoneNumber" placeholder="-없이 입력해주세요." v-model="credentials.tel" :rules="validateTel" class="disabled-input-bg" disabled />
      </div>
      <ErrorMessage class="error-message" name="informModifyPhoneNumber"/>
    </div>
    <button type="submit" class="sign-btn">수정하기</button>
  </Form>
</template>

<script>
import { ref } from 'vue'
import { Field, Form, ErrorMessage } from 'vee-validate';
import { useAccountStore } from '@/stores/land/account'

export default {
  name:'InformProfile',
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
    const birth = ref({
      year: '',
      month: '',
      day: '',
    })
    const credentials = ref({
      id: '',
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
    }
  }
}
</script>

<style>
#informModifyForm {
  height: calc(100% - 100px);
  margin-left: 32px;
  margin-right: -38px;
  overflow-x: hidden;
  overflow-y: auto;
}
#informModifyForm::-webkit-scrollbar {
  width: 10px;
}
#informModifyForm::-webkit-scrollbar-thumb {
  height: 30%;
  background: #A056FF;
  border-radius: 10px;
}
#informModifyForm::-webkit-scrollbar-track {
  background: rgba(160, 86, 255, .1);
}
#informModifyForm div {
  padding-top: 4px;
}
#informModifyForm div label {
  width: 44px;
  height: 20px;
  font-weight: 600;
  font-size: 16px;
  line-height: 20px;
  color: #000000;
}
#informModifyForm div input, select {
  box-sizing: border-box;
  width: 412px;
  height: 40px;
  background: #FFFFFF;
  border: 1px solid #C1BBBB;
  border-radius: 8px;
  padding: 10px;
}
#informModifyForm div .middle-input {
  width: 296px;
}
#informModifyForm div .sort-input {
  width: 132px;
  margin-right: 8px;
}
#informModifyForm button {
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
#informModifyForm div button {
  width: 104px;
}
#informModifyForm .sign-btn {
    width: 412px;
    margin: 32px 0px 4px 0px;
}
#informModifyForm button:hover {
  background: #8d39fc;
}
#informModifyForm button:active {
  background: #8122fe;
}
.error-message {
  font-size: 4px;
  color: red
}
#informModifyForm .disabled-input-bg {
  background-color: #efefef;
}
</style>