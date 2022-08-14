<template>
  <div class="sign-head text-center">
    <div>공지사항</div>
  </div>
  <table class="notice-table">
    <thead class="notice-table-title">
      <tr>
        <th class="table-title">공지 제목</th>
        <th class="table-date">생성 날짜</th>
      </tr>
    </thead>
    <tbody class="notice-table-content">
      <tr v-for="(notice, num) in admin.notices" :key="num">
        <td class="notice-detail-active" @click="noticeDetail(num)">{{ notice.title }}</td>
        <td class="table-date">{{ notice.createDate.slice(0, 10) }}</td>
      </tr>
    </tbody>
  </table>
</template>

<script>
import { storeToRefs } from 'pinia'
import { useMainStore } from '@/stores/main/main'
import { useAdminStore } from "@/stores/admin/admin";

export default {
  name: "AdminNotice",
  components: {},
  setup() {
    const main = useMainStore()
    const { btnCh } = storeToRefs(main)
    const admin = useAdminStore();
    admin.fetchNotices();
    return {
      admin,
      btnCh,
    };
  },
  methods: {
    noticeDetail(num) {
      this.btnCh = 6;
      this.admin.notice = this.admin.notices[num];
    }
  },
};
</script>

<style>
.table-title {
  text-align: center;
}
.table-date {
  width: 90px;
  text-align: center;
}
</style>