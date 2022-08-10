import { defineStore } from 'pinia'
import sr from '@/api/spring-rest'
import axios from 'axios'
import { useAccountStore } from '../land/account'
// import router from '@/router'

export const useAdminStore = defineStore('admin', {
  state: () => ({
    adminBtn: 0,
    noticeFormBtn: false,
    noticeBtn: 0,
    notices: [],
    notice: {},
    members: []
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

    fetchNotices() {
      axios({
        url: sr.notices.notices(),
        method: 'get',
      })
        .then(res => {
          console.log(res);
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
  }
})