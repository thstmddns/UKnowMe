import { defineStore } from 'pinia'
import sr from '@/api/spring-rest'
import axios from 'axios'
import { useAccountStore } from '../land/account'

export const useAdminStore = defineStore('admin', {
  state: () => ({
    adminBtn: 0,
    noticeFormBtn: false,
    noticeBtn: 0,
    notices: [],
    notice: {}
  }),
  getters: {

  },
  actions: {
    fetchNotices() {
      axios({
        url: sr.notices.notices(),
        method: 'get',
        params: {  }
      })
        .then(res => {
          console.log(res);
        })
        .catch(err => {
          console.error(err.response)
        })
    },

    // fetchNotice() {
    //   axios({
    //     url: sr.notices.notice(noticeSeq),
    //     method: 'get',
    //     params: { noticeSeq }
    //   })
    //     .then(res => {
    //       console.log(res);
    //     })
    //     .catch(err => {
    //       console.error(err.response)
    //     })
    // },
    
    addNotice(notice) {
      const account = useAccountStore()
      axios({
        url: sr.notices.save(),
        method: 'post',
        data: { ...notice },
        headers: account.authHeader,
      })
        .then(res => {
          console.log(res);
        })
        .catch(err => {
          console.error(err.response.data)
        })

    },

    // updateNotice() {
    //   axios({
    //     url: sr.notices.notice(noticeSeq),
    //     method: 'get',
    //     params: { noticeSeq }
    //   })
    //     .then(res => {
    //       console.log(res);
    //     })
    //     .catch(err => {
    //       console.error(err.response)
    //     })
    // },

    // deleteNotice(noticeSeq) {
    //   const account = useAccountStore()
    //   if (confirm('정말 삭제하시겠습니까?')) {
    //     axios({
    //       url: sr.notices.notice(noticeSeq),
    //       method: 'delete',
    //       headers: account.authHeader,
    //     })
    //       .then(res => {
    //         console.log(res);
    //       })
    //       .catch(err => console.error(err.response))
    //   }
    // },
  }
})