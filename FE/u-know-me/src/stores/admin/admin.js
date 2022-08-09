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
    notices: [{name: '관리자', title: '제목', content: '내용', time: '오늘'},
    {name: '관리자', title: '제목2', content: '내용', time: '오늘'},
    {name: '관리자', title: '제목3', content: '내용', time: '오늘'},
    {name: '관리자', title: '제목4', content: '내용', time: '오늘'},
    {name: '관리자', title: '제목5', content: '내용', time: '오늘'},
  ],
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
        params: {  }
      })
        .then(res => {
          console.log(res);
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
          this.noticeBtn = 2
          this.fetchNotices()
        })
        .catch(err => {
          console.error(err.response.data)
          this.noticeBtn = 2
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
          })
          .catch(err => console.error(err.response))
      }
    },
  }
})