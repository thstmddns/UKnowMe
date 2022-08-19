import axios from 'axios'
import { defineStore } from 'pinia'
import sr from '@/api/spring-rest'
import { useAccountStore } from '../land/account'

export const useAdminStore = defineStore('admin', {
  state: () => ({
    adminBtn: 0,
    noticeFormBtn: false,
    noticeAddBtn: false,
    balanceBtn: 0,
    noticeBtn: 0,
    notices: [],
    notice: {},
    members: [],
    member: {},
    balanceList: [],
    balance: {},
    keywordList: [],
    keyword: {},

  }),
  getters: {

  },
  actions: {
    fetchMembers() {
      const account = useAccountStore()
      axios({
        url: sr.members.members(),
        method: 'get',
        headers: account.authHeader,
      })
      .then(res => {
        console.log(res);
        this.members = res.data
      })
      .catch(err => {
        console.error(err.response)
      })
    },
    fetchMember(memberSeq) {
      const account = useAccountStore()
      axios({
        url: sr.members.memberDetail(memberSeq),
        method: 'get',
        headers: account.authHeader,
      })
      .then(res => {
        console.log(res);
        this.member = res.data
      })
      .catch(err => {
        console.error(err.response)
      })
    },
    fetchNotices() {
      const account = useAccountStore()
      axios({
        url: sr.notices.notices(),
        method: 'get',
        headers: account.authHeader,
      })
        .then(res => {
          console.log(res.data);
          this.notices = res.data
        })
        .catch(err => {
          console.error(err.response)
        })
    },
    fetchNotice(noticeSeq) {
      axios({
        url: sr.notices.notice(noticeSeq),
        method: 'get',
      })
        .then(res => {
          console.log(res);
          this.notice = res.data
        })
        .catch(err => {
          console.error(err.response)
        })
    },
    addNotice(notice) {
      console.log({...notice})
      const account = useAccountStore()
      axios({
        url: sr.notices.save(),
        method: 'post',
        data: { ...notice },
        headers: account.authHeader,
      })
        .then(res => {
          console.log(res);
          this.noticeFormBtn = false
          // this.noticeBtn = 2
          this.fetchNotices()
        })
        .catch(err => {
          console.error(err.response.data)
          // this.noticeBtn = 2
        })

    },
    updateNotice(noticeData, noticeSeq) {
      console.log({...noticeData})
      const account = useAccountStore()
      axios({
        url: sr.notices.notice(noticeSeq),
        method: 'put',
        data: { ...noticeData },
        headers: account.authHeader,
      })
        .then(res => {
          console.log(res);
          this.fetchNotices()
          this.fetchNotice(noticeSeq)
          this.noticeBtn = 1
        })
        .catch(err => {
          console.error(err.response)
        })
    },
    deleteNotice(noticeSeq) {
      const account = useAccountStore()
      if (confirm('정말 삭제하시겠습니까?')) {
        axios({
          url: sr.notices.notice(noticeSeq),
          method: 'delete',
          headers: account.authHeader,
        })
          .then(res => {
            console.log(res);
            this.fetchNotices()
            this.noticeBtn = 0
          })
          .catch(err => console.error(err.response))
      }
    },
    getBalances() {
      const account = useAccountStore()
      axios({
        url: sr.features.balanceList(),
        method: 'get',
        params: { },
        headers: account.authHeader,
      })
        .then(res => {
          console.log(res.data);
          this.balanceList = res.data
        })
        .catch(err => {
          console.error(err.response.data)
        })
    },
    getBalance(balanceSeq) {
      const account = useAccountStore()

      axios({
        url: sr.features.balanceInfo(balanceSeq),
        method: 'get',
        params: { },
        headers: account.authHeader,
      })
        .then(res => {
          console.log(res);
          this.balance = res.data
        })
        .catch(err => {
          console.error(err.response)
        })
    },
    addBalance(balance) {
      console.log({ ...balance })
      const account = useAccountStore()
      axios({
        url: sr.features.balance(),
        method: 'post',
        data: { ...balance },
        headers: account.authHeader,
      })
        .then(res => {
          console.log(res);
          this.getBalances()
        })
        .catch(err => {
          console.error(err.response.data)
        })
    },

    updatingBalance(balanceData, balanceSeq) {
      console.log({...balanceData})
      const account = useAccountStore()
      axios({
        url: sr.features.balanceInfo(balanceSeq),
        method: 'put',
        data: { ...balanceData },
        headers: account.authHeader,
      })
      .then(res => {
        console.log(res);
        this.getBalances()
        this.getBalance(balanceSeq)
        this.balanceBtn = 1
      })
      .catch(err => {
        console.error(err.response)
      })
    },

    updateBalance(balanceData, balanceSeq) {
      console.log({...balanceData})
      const account = useAccountStore()
      axios({
        url: sr.features.balanceInfo(balanceSeq),
        method: 'put',
        data: { ...balanceData },
        headers: account.authHeader,
      })
      .then(res => {
        console.log(res);
        this.getBalances()
        this.noticeBtn = 0
      })
      .catch(err => {
        console.error(err.response)
      })
    },

    deleteBalance(balanceSeq) {
      const account = useAccountStore()
      if (confirm('정말 삭제하시겠습니까?')) {
        axios({
          url: sr.features.balanceInfo(balanceSeq),
          method: 'delete',
          headers: account.authHeader,
        })
        .then(res => {
          console.log(res);
          this.getBalances()
        })
        .catch(err => console.error(err.response))
      }
    },
    getKeywords() {
      const account = useAccountStore()
      axios({
        url: sr.features.keywordList(),
        method: 'get',
        params: {},
        headers:account.authHeader,
      })
      .then(res => {
        console.log(res.data);
        this.keywordList = res.data
      })
      .catch(err => {
        console.error(err.response.data)
      })
    },

    getKeyword(keywordSeq) {
      const account = useAccountStore()

      axios({
        url: sr.features.keywordInfo(keywordSeq),
        method: 'get',
        params: {},
        headers: account.authHeader,
      })
      .then(res => {
        console.log(res);
        this.keyword = res.data
      })
      .catch(err => {
        console.error(err.response)
      })
    },

    addKeyword(keyword) {
      console.log({ ...keyword })
      const account = useAccountStore()
      axios({
        url:sr.features.keyword(),
        method: 'post',
        data: { ...keyword },
        headers: account.authHeader,
      })
      .then(res => {
        console.log(res);
        this.getKeywords()
      })
      .catch(err => {
        console.error(err.response.data)
      })
    },

    updateKeyword(keywordData, keywordSeq) {
      console.log({...keywordData})
      const account = useAccountStore()
      axios({
        url: sr.features.keywordInfo(keywordSeq),
        method: 'put',
        data: { ...keywordData },
        headers: account.authHeader,
      })
      .then(res => {
        console.log(res);
        this.getKeywords()
        this.noticeBtn = 0
      })
      .catch(err => {
        console.error(err.response)
      })
    },
    deleteKeyword(keywordSeq) {
      const account = useAccountStore()
      if (confirm('정말 삭제하겠습니까>')) {
        axios({
          url: sr.features.keywordInfo(keywordSeq),
          method: 'delete',
          headers: account.authHeader,
        })
        .then(res => {
          console.log(res);
          this.getKeywords()
        })
        .catch(err => console.error(err.response))
      }
    }
  },
})