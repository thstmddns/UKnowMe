<template>
  <div class="notice-detail-modal-title">
    <div class="notice-detail-title">{{admin.notice.title}}</div>
  </div>
  <div class="notice-detail-modal-content">
    <pre class="notice-detail-content">{{admin.notice.content}}</pre>
  </div>
  <div class="notice-btn-list">
    <!-- {{admin.notice}} -->
    <button class="admin-btn" @click="admin.noticeBtn = 2">수정</button>
    <button @click="admin.deleteNotice(admin.notice.seq)" class="admin-btn">삭제</button>
  </div>
</template>

<script>
import { storeToRefs } from "pinia";
import { useAdminStore } from "@/stores/admin/admin";

export default {
  name: "NoticForm",
  components: {},
  setup() {
    const admin = useAdminStore()
    const { noticeBtn } = storeToRefs(admin)

    return {
      admin,
      noticeBtn,
    };
  },
}
</script>

<style>
.notice-detail-modal-title {
  margin-top: 2px;
  padding: 2% 11%;
}
.notice-detail-title {
  margin-bottom: 0;
  font-size: 30px;
  font-weight: 600;
}
.notice-detail-modal-content {
  border: 1px solid #c1c3fc;
  border-radius: 10px;
  margin: 7%;
  height: 220px;
}
.notice-detail-content {
  font-size: 15px;
  padding: 4%;
  margin: 0;
}
.notice-btn-list {
  margin: 3%;
  display: flex;
  justify-content: space-around;
}
</style>