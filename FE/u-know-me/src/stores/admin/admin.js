import axios from 'axios'
import { defineStore } from 'pinia'
import sr from '@/api/spring-rest'
import { useAccountStore } from '../land/account'

export const useAdminStore = defineStore('admin', {
  state: () => ({
    adminBtn: 0,
    noticeAddBtn: false,
    balanceList: [],
    balance: {},
  }),
  getters: {

  },
  actions: {
    getBalances() {
      const account = useAccountStore()
      axios({
        url: sr.features.balanceList(),
        method: 'get',
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
      axios({
        url: sr.features.balanceInfo(balanceSeq),
        method: 'get',
        params: { }
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

    updateBalance(balanceData, balanceSeq) {
      console.log({...balanceData})
      const account = useAccountStore()
      axios({
        url: sr.features.balanceInfo(balanceSeq),
        method: 'put',
        data: { ... balanceData },
        headers: account.authHeader,
      })
      .then(res => {
        console.log(res);
        this.getBalances()
        this.getBalance(balanceSeq)
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
  },
})