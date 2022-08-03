import { defineStore } from 'pinia'
import sr from '@/api/spring-rest'
import router from '@/router'
import axios from 'axios'
import { useLandStore } from './land'

export const useAccountStore = defineStore('account', {
  state: () => ({
    token: '',
    currentUser: {},
    profile: {},
    authError: null,
    isAdmin: false,
  }),
  getters: {
    isLoggedIn: state => !!state.token,
    authHeader: state => ({ Authorization: `Token ${state.token}`}),
  },
  actions: {
    getToken() {
      return localStorage.getItem('id_token')
    },
    saveToken(token) {
      this.token = token
      localStorage.setItem('id_token', token)
    },
    removeToken() {
      this.token = ''
      localStorage.setItem('id_token', '')
    },
    signup(credentials, birth) {
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
          // router.push({ name: 'home' })
        })
        .catch(err => {
          console.error(err.response.data)
          this.authError = err.response.data
        })
    },
    login(credentials) {
      console.log({...credentials})
      // router.push({ name: 'main' })
      axios({
        url: sr.members.login(),
        method: 'post',
        data: {...credentials}
      })
        .then(res => {
          console.log(res.data);
          // const token = res.data.key
          // this.saveToken(token)
          // this.fetchCurrentUser()
          // this.fetchIsAdmin(credentials)
          // router.push({ name: 'home' })
        })
        .catch(err => {
          console.error(err.response.data)
          this.authError = err.response.data
        })
    },
    logout() {
      axios({
        url: sr.accounts.logout(),
        method: 'post',
        // data: {},
        headers: this.authHeader,
      })
        .then(() => {
          this.removeToken()
          this.isAdmin = false
          router.push({ name: 'home' })
        })
        .error(err => {
          console.error(err.response)
        })
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
    }
  },
})