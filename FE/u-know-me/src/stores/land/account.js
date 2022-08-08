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
        birth.day = '0'+ birth.day
      }
      credentials.birth = birth.year.slice(-2) + birth.month + birth.day
      console.log('회원가입', {...credentials})
      axios({
        url: sr.members.signup(),
        method: 'post',
        data: {...credentials}
      })
        .then(res => {
          console.log(res);
          alert('회원가입이 완료되었습니다. 새로운 환경에서 로그인 해주세요.')
          land.btnCh=1
        })
        .catch(err => {
          console.error(err.response.data)
        })
    },
    login(credentials) {
      console.log({...credentials})
      axios({
        url: sr.members.login(),
        method: 'post',
        data: {...credentials},
        withCredentials: true,
      })
        .then(res => {
          console.log(res);
          const access_token = res.headers.authorization.split(' ')[1]
          const refresh_token = res.headers.temp
          this.saveToken(access_token, refresh_token)
          this.fetchCurrentUser()
          // this.fetchIsAdmin(credentials)
          this.authError.login = 0
          router.push({ name: 'main' })
        })
        .catch(err => {
          console.error(err.response.data)
          this.authError.login = 1
        })
    },
    logout() {
      this.removeToken()
      router.push({ name: 'home' })
    },
    socialLogin(sns, credentials) {
      const land = useLandStore()
      if (!credentials) {
        console.log('sns 로그인하기')
      } else {
        console.log('최초 1회 로그인하기')
        land.btnCh=3
      }
    },
    naverLogin(token) {
      console.log(token)
      // axios({
      //   url: sr.accounts.naverLogin(),
      //   method: 'post',
      //   data: { naverLoginSeq: token },
      //   headers: this.authHeader,
      // })
      //   .then((res) => {
      //     console.log(res)
      //   })
      //   .error(err => {
      //     console.error(err.response)
      //   })
    },
    kakaoLogin() {
      const js_key = "29f50216ed786d04f88f2f1aafdd49cc"
      const Kakao = window.Kakao
      Kakao.init(js_key);
      function loginWithKakao() {
        Kakao.Auth.login({
          success: function(authObj) {
            alert(JSON.stringify(authObj))
            console.log(authObj);
          },
          fail: function(err) {
            alert(JSON.stringify(err))
          },
        })
      }
      loginWithKakao()
      // axios({
      //   url: sr.accounts.kakaoLogin(),
      //   method: 'post',
      //   data: { kakaoLoginSeq: token },
      //   headers: this.authHeader,
      // })
      //   .then((res) => {
      //     console.log(res)
      //   })
      //   .error(err => {
      //     console.error(err.response)
      //   })
    },
    googleLogin() {
      const Google = window.gapi
      console.log(Google);
    },
    findId(credentials) {
      const land = useLandStore()
       axios({
        url: sr.members.findId(),
        method: 'get',
        params: {...credentials},
      })
        .then((res) => {
          this.findUserId = res.data.id
          land.btnCh = 6
        })
        .error(err => {
          console.error(err.response)
          alert('일치하는 사용자가 없습니다.')
        })
    },
    findPassword(credentials) {
      console.log({...credentials})
      //  axios({
      //   url: sr.accounts.findPassword(),
      //   method: 'post',
      //   data: {...credentials},
      // })
      //   .then((res) => {
      //     console.log(res)
      //     this.findUserfindPassword = res
      //   })
      //   .error(err => {
      //     console.error(err.response)
      //   })
    },
    fetchCurrentUser() {
      if (this.isLoggedIn) {
        axios({
          url: sr.members.member(),
          method: 'get',
          headers: this.authHeader,
        })
          .then(res => {
            this.currentUser = res.data
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
      console.log({password});
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
      console.log({password});
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
      console.log({...credentials});
      const main = useMainStore()
      axios({
        url: sr.members.update(),
        method: 'put',
        data: {...credentials},
        headers: this.authHeader,
      })
        .then(res => {
          console.log(res);
          main.btnCh = 0
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
      console.log({ id });
      axios({
        url: sr.members.idDuplicate(),
        method: 'get',
        params: { id }
      })
        .then(res => {
          console.log(res);
          this.checkSign.id = 1
        })
        .catch(err => {
          console.error(err.response)
          this.checkSign.id = 0
        })
    },
    duplicateNickname(nickname) {
      console.log({ nickname });
      axios({
        url: sr.members.nickNameDuplicate(),
        method: 'get',
        params: { nickname }
      })
        .then(res => {
          console.log(res);
          this.checkSign.nickName = 1
        })
        .catch(err => {
          console.error(err.response)
          this.checkSign.nickName = 0
        })
    },
    sendNumTel(tel) {
      tel
      this.sendTel = 1
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
    }
  },
})