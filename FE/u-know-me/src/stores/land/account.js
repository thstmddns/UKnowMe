import { defineStore } from 'pinia'
import sr from '@/api/spring-rest'
import router from '@/router'
import axios from 'axios'
import { useLandStore } from './land'
import { useCookies } from "vue3-cookies";
import { useMainStore } from '../main/main'

const { cookies } = useCookies();

export const useAccountStore = defineStore('account', {
  state: () => ({
    a_token: cookies.get('UkmL') || '',
    r_token: cookies.get('RUkmL') || '',
    currentUser: {},
    profile: {},
    authError: null,
    isAdmin: false,
    findUserId: '',
    findUserfindPassword: '',
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
      Refresh: state.r_token
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
          this.authError = err.response.data
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
          // this.fetchCurrentUser()
          // this.fetchIsAdmin(credentials)
          router.push({ name: 'main' })
        })
        .catch(err => {
          console.error(err.response.data)
          this.authError = err.response.data
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
      console.log({...credentials})
      //  axios({
      //   url: sr.accounts.findId(),
      //   method: 'post',
      //   data: {...credentials},
      // })
      //   .then((res) => {
      //     console.log(res)
      //     this.findUserId = res
      //   })
      //   .error(err => {
      //     console.error(err.response)
      //   })
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
          url: sr.accounts.currentUserInfo(),
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
    fetchProfile({ username }) {
      axios({
        url: sr.accounts.profile(username),
        method: 'get',
        headers: this.authHeader,
      })
        .then(res => {
          this.profile = res.data
        })
        .catch(err => {
          console.error(err.response)
        })
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
    duplicateId(id) {
      if (id === 'user99') {
        this.checkSign.id = 1
      } else {
        this.checkSign.id = 0
      }
      // axios({
      //   url: sr.members.idDuplicate(),
      //   method: 'get',
      //   data: { id }
      // })
      //   .then(res => {
      //     console.log(res);
      //     this.checkSign.id = 1
      //   })
      //   .catch(err => {
      //     console.error(err.response)
      //     this.checkSign.id = 0
      //   })
    },
    duplicateNickname(nickname) {
      if (nickname === 'user99') {
        this.checkSign.nickName = 1
      } else {
        this.checkSign.nickName = 0
      }
      // axios({
      //   url: sr.members.nickNameDuplicate(),
      //   method: 'get',
      //   data: { nickname }
      // })
      //   .then(res => {
      //     console.log(res);
      //     this.checkSign.nickName = 1
      //   })
      //   .catch(err => {
      //     console.error(err.response)
      //     this.checkSign.nickName = 0
      //   })
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
})