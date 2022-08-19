<template>
  <div class="sign-head text-center">
    <div>공지사항</div>
  </div>
  <div class="notice-table">
      <div class="notice-table-title">
        <table>
          <thead>
            <tr>
              <th class="table-title">공지 제목</th>
              <th class="table-date">생성 날짜</th>
            </tr>
          </thead>
        </table>
      </div>
      <div class="main-notice-table-content">
        <table>
          <tbody>
            <tr v-for="(notice, num) in admin.notices" :key="num" class="notice-detail-active" @click="noticeDetail(num)">
              <td>{{ notice.title }}</td>
              <td class="table-date">{{ notice.createDate.slice(0, 10) }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
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
.main-notice-table-content {
  height: 250px;
  overflow-x:auto;
  margin-top: 0px;
  border: 1px solid #f0f1ff;
}
.main-notice-table-content::-webkit-scrollbar {
  width: 10px;
}
.main-notice-table-content::-webkit-scrollbar-thumb {
  background: #c1c3fc;
  border-radius: 10px;
}
</style>