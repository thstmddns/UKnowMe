import { defineStore } from 'pinia'
import sr from '@/api/spring-rest'
import router from '@/router'
import axios from 'axios'
import { useLandStore } from './land'
import { useMainStore } from '../main/main'
import { useCookies } from "vue3-cookies";

const { cookies } = useCookies();

export const useAccountStore = defineStore('account', {
  state: () => ({
    a_token: cookies.get('UkmL') || '',
    r_token: cookies.get('RUkmL') || '',
    currentUser: {},
    authError: {
      login: 0,
    },
    isAdmin: false,
    findUserId: '',
    findUserfindPassword: '',
    correctPassword: 0,
    checkSign: {
      id: 0,
      nickName: 0,
      tel: 0,
    },
    sendTel: 0,
  }),
  getters: {
    isLoggedIn: state => !!state.a_token,
    authHeader: state => ({
      Authorization: `Bearer ${state.a_token}`,
      refreshToken: state.r_token
    }),
  },
  actions: {
    getToken() {
      this.a_token = cookies.get('UkmL')
      this.r_token = cookies.get('RUkmL')
    },
    saveToken(a_token, r_token) {
      this.a_token = a_token
      this.r_token = r_token
      cookies.set('UkmL', a_token, '2h')
      cookies.set('RUkmL', r_token, '7d')
    },
    removeToken() {
      this.a_token = ''
      this.r_token = ''
      cookies.remove('UkmL')
      cookies.remove('RUkmL')
    },
    signup(credentials, birth) {
      const land = useLandStore()
      if (birth.day.length === 1) {
        birth.day = '0' + birth.day
      }
      credentials.birth = birth.year + birth.month + birth.day
      console.log('회원가입', {...credentials})
      axios({
        url: sr.members.signup(),
        method: 'post',
        data: { ...credentials }
      })
        .then(res => {
          console.log(res);
          alert('회원가입이 완료되었습니다. 새로운 환경에서 로그인 해주세요.')
          land.btnCh = 1
        })
        .catch(err => {
          console.error(err.response.data)
        })
    },
    async login(credentials) {
      console.log({...credentials})
      await axios({
        url: sr.members.login(),
        method: 'post',
        data: { ...credentials },
        withCredentials: true,
      })
        .then(res => {
          console.log(res);
          this.authError.login = 0
          const access_token = res.headers.authorization.split(' ')[1]
          const refresh_token = res.headers.temp
          this.saveToken(access_token, refresh_token)
        })
        .catch(err => {
          console.error(err)
          this.authError.login = 1
        })
      if (!this.authError.login) {
        await this.fetchCurrentUser()
        if(this.currentUser.role === "ROLE_USER") {
          router.push({ name: 'main' })
        } else {
          router.push({ name: 'admin' })
        }
      }
    },
    logout() {
      const main = useMainStore()
      this.removeToken()
      main.$reset()
      router.push({ name: 'home' })
    },
    socialLogin() {
      const land = useLandStore()
      const access_token = cookies.get('access_token')
      const refresh_token = cookies.get('refresh_token')
      this.saveToken(access_token, refresh_token)
      if (access_token) {
        router.push({ name: 'main' })
        cookies.remove('access_token')
        cookies.remove('refresh_token')
      } else {
        alert('회원가입을 먼저 해주세요.')
        land.btnCh = 2
      }
    },
    naverLogin() {
      const REIDRECT_URL = 'https://uknowme.mooo.com:8443/oauth2/authorization/naver?redirect_uri=https://uknowme.mooo.com:8443/member/oauth2/code/naver'
      window.open(REIDRECT_URL, '네이버로그인', this.getTelPopupFeatures());
    },
    kakaoLogin() {
      const REIDRECT_URL = 'https://uknowme.mooo.com:8443/oauth2/authorization/kakao?redirect_uri=https://uknowme.mooo.com:8443/member/oauth2/code/kakao'
      window.open(REIDRECT_URL, '카카오로그인', this.getTelPopupFeatures());
    },
    getTelPopupFeatures() {
      var popupWidth = 480;
      var popupHeight = 720;
      var sLeft = window.screenLeft ? window.screenLeft : window.screenX ? window.screenX : 0;
      var sTop = window.screenTop ? window.screenTop : window.screenY ? window.screenY : 0;
      var popupLeft = screen.width / 2 - popupWidth / 2 + sLeft;
      var popupTop = screen.height / 2 - popupHeight / 2 + sTop;
      return ["width=".concat(popupWidth), "height=".concat(popupHeight), "left=".concat(popupLeft), "top=".concat(popupTop), 'scrollbars=yes', 'resizable=1'].join(',');
    },
    findId(credentials) {
      console.log({...credentials});
      const land = useLandStore()
      axios({
        url: sr.members.findId(),
        method: 'get',
        params: { ...credentials },
      })
        .then((res) => {
          if (res.data) {
            this.findUserId = res.data.id
            land.btnCh = 6
          } else {
            alert('일치하는 사용자가 없습니다.')
          }
        })
        .catch(err => {
          console.error(err.response)
        })
    },
    findPassword(credentials) {
      console.log({ ...credentials })
      //  axios({
      //   url: sr.accounts.findPassword(),
      //   method: 'post',
      //   data: {...credentials},
      // })
      //   .then((res) => {
      //     console.log(res)
      //     this.findUserfindPassword = res
      //   })
      //   .catch(err => {
      //     console.error(err.response)
      //   })
    },
    async fetchCurrentUser() {
      if (this.isLoggedIn) {
        await axios({
          url: sr.members.member(),
          method: 'get',
          headers: this.authHeader,
        })
          .then(res => {
            this.currentUser = res.data
            console.log(res.data);
          })
          .catch(err => {
            if (err.response.status == 401) {
              this.removeToken()
              router.push({ name: 'home' })
            }
          })
      }
    },
    fetchIsAdmin({ username }) {
      axios({
        url: sr.accounts.isAdmin(username),
        method: 'get',
        headers: this.authHeader,
      })
        .then(res => {
          this.isAdmin = res.data.is_supersuser
        })
        .catch(err => {
          console.error(err.response)
        })
    },
    certificatePassword(password) {
      console.log({ password });
      const main = useMainStore()
      this.fetchCurrentUser()
      axios({
        url: sr.members.validatePassword(),
        method: 'post',
        data: { password },
        headers: this.authHeader,
      })
        .then(res => {
          console.log(res);
          if (res.data) {
            main.btnCh = 3
            main.pBtnCh = 1
          } else {
            alert('비밀번호를 다시 확인해주세요')
          }
        })
        .catch(err => {
          console.error(err.response)
          alert('비밀번호가 일치하지 않습니다.')
        })
    },
    modifyCertificatePassword(password) {
      console.log({ password });
      axios({
        url: sr.members.validatePassword(),
        method: 'post',
        data: { password },
        headers: this.authHeader,
      })
        .then(res => {
          console.log(res);
          this.correctPassword = 1
        })
        .catch(err => {
          console.error(err.response)
          this.correctPassword = 0
        })
    },
    modifyInform(credentials) {
      console.log({ ...credentials });
      const main = useMainStore()
      axios({
        url: sr.members.update(),
        method: 'put',
        data: { ...credentials },
        headers: this.authHeader,
      })
        .then(res => {
          console.log(res);
          main.btnCh = 0
          main.pBtnCh = 0
          alert('성공적으로 정보가 변경되었습니다.')
        })
        .catch(err => {
          console.error(err.response)
        })
    },
    chagePassword(password) {
      console.log(password);
      // axios({
      //   url: sr.members.member(),
      //   method: 'put',
      //   data: { password },
      //   headers: this.authHeader,
      // })
      //   .then(res => {
      //     console.log(res);
      //     main.btnCh = 0
      //     alert('성공적으로 비밀번호가 변경되었습니다.')
      //   })
      //   .catch(err => {
      //     console.error(err.response)
      //   })
    },
    duplicateId(id) {
      axios({
        url: sr.members.idDuplicate(),
        method: 'get',
        params: { id }
      })
        .then(res => {
          if (res.data) {
            this.checkSign.id = 1
          } else {
            this.checkSign.id = 0
          }
        })
        .catch(err => {
          console.error(err.response)
          this.checkSign.id = 0
        })
    },
    duplicateNickname(nickname) {
      axios({
        url: sr.members.nickNameDuplicate(),
        method: 'get',
        params: { nickname }
      })
        .then(res => {
          if (res.data) {
            this.checkSign.nickName = 1
          } else {
            this.checkSign.nickName = 0
            alert('중복된 닉네임이 있습니다.')
          }
        })
        .catch(err => {
          console.error(err.response)
          this.checkSign.nickName = 0
        })
    },
    sendNumTel(tel) {
      // popup
      function openTelPage(pn) {
        window.open(`http://localhost:8080/tc?pn=${pn}`, 'Pass인증', getTelPopupFeatures());
      }
      function getTelPopupFeatures() {
        var popupWidth = 480;
        var popupHeight = 720;
        var sLeft = window.screenLeft ? window.screenLeft : window.screenX ? window.screenX : 0;
        var sTop = window.screenTop ? window.screenTop : window.screenY ? window.screenY : 0;
        var popupLeft = screen.width / 2 - popupWidth / 2 + sLeft;
        var popupTop = screen.height / 2 - popupHeight / 2 + sTop;
        return ["width=".concat(popupWidth), "height=".concat(popupHeight), "left=".concat(popupLeft), "top=".concat(popupTop), 'scrollbars=yes', 'resizable=1'].join(',');
      }
      // !popup
      const phoneJ = /^01([0|1|6|7|8|9]?)?([0-9]{3,4})?([0-9]{4})$/;
      if (!phoneJ.test(tel)) {
        alert('형식에 맞지 않는 번호입니다.')
      } else {
        this.sendTel = 1
        openTelPage(tel)
      }
    },
    certicateTel(num) {
      if (num === '0000') {
        this.checkSign.tel = 1
      } else {
        this.checkSign.tel = 0
      }
    },
    deleteAccount() {
      const main = useMainStore()
      if (confirm('정말로 탈퇴하시겠습니까?')) {
        axios({
          url: sr.members.member(),
          method: 'delete',
          headers: this.authHeader,
        })
          .then(res => {
            console.log(res);
            this.removeToken()
            this.isAdmin = false
            alert('회원탈퇴가 성공적으로 되었습니다.')
            main.$reset()
            router.push({ name: 'home' })
          })
          .catch(err => {
            console.error(err.response)
            alert('회원탈퇴에 실패하셨습니다.')
          })
      }
    },
  },
})