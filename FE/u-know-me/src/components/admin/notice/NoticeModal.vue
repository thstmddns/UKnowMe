<template>
  <div class="notice-detail-modal-bg">
      <div class="notice-detail-modal"
      :class="{
        'notice-detail-modal-sub' : noticeBtn == 1,
        'notice-edit-modal-sub' : noticeBtn == 2,
        }">
        <div class="close-btn" @click="noticeBtn = 0">
          <i class="fa-solid fa-xmark x-btn"></i>
        </div>
        <div class="modal-content"
        :class="{
          'modal-content-sub' : noticeBtn == 1,
          'notice-edit-sub' : noticeBtn == 2,
          'member-edit-sub' : noticeBtn == 3,
        }">
          <notice-detail v-if="noticeBtn == 1"/>
          <notice-edit v-if="noticeBtn == 2"/>
          <member-detail v-if="noticeBtn == 3"/>
          <balance-edit v-if="noticeBtn == 4"/>
          <keyword-edit v-if="noticeBtn == 5"/>
        </div>
      </div>
    </div>
</template>

<script>
import { storeToRefs } from "pinia"
import { useAdminStore } from "@/stores/admin/admin"
import NoticeDetail from '@/components/admin/notice/NoticeDetail.vue'
import NoticeEdit from '@/components/admin/notice/NoticeEdit.vue'
import MemberDetail from '@/components/admin/member/MemberDetail.vue'
import BalanceEdit from '@/components/admin/balance/BalanceEdit.vue'
import KeywordEdit from '../keyword/KeywordEdit.vue'

export default {
  name: "NoticForm",
  components: {
    NoticeDetail, NoticeEdit, MemberDetail, BalanceEdit,
    KeywordEdit,
  },
  setup() {
    const admin = useAdminStore()
    const { noticeBtn } = storeToRefs(admin)

    return {
      admin,
      noticeBtn,
      BalanceEdit,
      KeywordEdit
    };
  },
}
</script>

<style>
.notice-detail-modal-bg {
  width: 100vw;
  height: 100vh;
  z-index: 9;
  background: rgba(0, 0, 0, 0.5);
  position: fixed;
  top: 0;
  left: 0;
}

.notice-detail-modal {
  position: relative;
  width: 25%;
  background: #ffffff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 27px;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.notice-detail-modal-sub {
  width: 40%;
}
.notice-edit-modal-sub {
  width: 40%;
}
.modal-content {
  /* width: 100%;
  height: 100%; */
  /* display: flex;
  justify-content: center;
  align-items: center; */
}
.modal-content-sub {
  padding: 20px;
}
.notice-edit-sub {
  padding: 20px;
}
.member-edit-sub {
  padding: 20px;
}
</style>